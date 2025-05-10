package Games.Games.Service;

import Games.Games.Model.VideogameModel;
import Games.Games.util.Game;
import Games.Games.util.GameEntrada;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideogameService {

    private final String API_KEY = "be091db890da48d8bb03eabfc134546b";
    private final String API_URL = "https://api.rawg.io/api/games?key=" + API_KEY;


    private final List<String> favoriteGames = new ArrayList<>();

    // Método para retornar a lista de games por página
    public List<VideogameModel> getGameData(int page) {
        RestTemplate restTemplate = new RestTemplate();
        List<VideogameModel> videogames = new ArrayList<>();
        int pageSize = 40;
        String url = API_URL + "&page=" + page + "&page_size=" + pageSize;

        GameEntrada response = restTemplate.getForObject(url, GameEntrada.class);

        if (response != null && response.getResults() != null) {
            for (Game game : response.getResults()) {
                VideogameModel model = new VideogameModel();
                model.setName(game.getName());
                model.setReleased(game.getReleased());
                model.setBackground_image(game.getBackground_image());
                model.setMetacritic(game.getMetacritic() != null ? game.getMetacritic().toString() : "N/A");

                if (game.getPlatforms() != null && !game.getPlatforms().isEmpty()) {
                    List<String> plataformas = game.getPlatforms().stream()
                            .map(p -> p.getPlatform().getName())
                            .collect(Collectors.toList());
                    model.setPlatforms(plataformas);
                } else {
                    model.setPlatforms(List.of("Desconhecido"));
                }


                if (game.getGenres() != null) {
                    List<String> generos = game.getGenres().stream()
                            .map(g -> g.getName())
                            .collect(Collectors.toList());
                    model.setGenres(generos);
                }

                videogames.add(model);
            }
        }

        return videogames;
    }

//Método para procurar os games da Api pelo parâmetro search
    public List<VideogameModel> searchGamesByName(String searchTerm, int totalPages) {
        RestTemplate restTemplate = new RestTemplate();
        List<VideogameModel> resultGames = new ArrayList<>();

        for (int page = 1; page <= totalPages; page++) {
            String url = API_URL + "&search=" + UriUtils.encode(searchTerm, StandardCharsets.UTF_8)
                    + "&page=" + page + "&page_size=20";

            GameEntrada response = restTemplate.getForObject(url, GameEntrada.class);

            if (response != null && response.getResults() != null) {
                for (Game game : response.getResults()) {
                    VideogameModel model = new VideogameModel();
                    model.setName(game.getName());
                    model.setReleased(game.getReleased());
                    model.setBackground_image(game.getBackground_image());
                    model.setMetacritic(game.getMetacritic() != null ? game.getMetacritic().toString() : "N/A");

                    if (game.getPlatforms() != null && !game.getPlatforms().isEmpty()) {
                        List<String> plataformas = game.getPlatforms().stream()
                                .map(p -> p.getPlatform().getName())
                                .collect(Collectors.toList());
                        model.setPlatforms(plataformas);
                    } else {
                        model.setPlatforms(List.of("Desconhecido"));
                    }


                    if (game.getGenres() != null) {
                        List<String> generos = game.getGenres().stream()
                                .map(g -> g.getName())
                                .collect(Collectors.toList());
                        model.setGenres(generos);
                    }

                    resultGames.add(model);
                }
            }
        }

        return resultGames;
    }

//Método para retornar lista de games baseado pela nota do Metacritic
    public List<VideogameModel> getTopRatedGames(int quantidade, int page) {
        return getGameData(page).stream()
                .filter(g -> {
                    try {
                        Integer.parseInt(g.getMetacritic());
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .sorted((g1, g2) -> Integer.compare(Integer.parseInt(g2.getMetacritic()), Integer.parseInt(g1.getMetacritic())))
                .limit(quantidade)
                .toList();
    }


//Método para adicionar games em uma lista de favoritos pelo nome
    public void addFavoriteGame(String gameName) {
        favoriteGames.add(gameName);
    }

    //Método para retornar a lista de games favoritos
    public List<VideogameModel> getFavoriteGames() {
        List<VideogameModel> favoritosEncontrados = new ArrayList<>();
        int page = 1;
        Set<String> favoritosRestantes = new HashSet<>(favoriteGames);

        while (!favoritosRestantes.isEmpty()) {
            List<VideogameModel> paginaJogos = getGameData(page);
            if (paginaJogos.isEmpty()) break;

            for (VideogameModel jogo : paginaJogos) {
                if (favoritosRestantes.contains(jogo.getName())) {
                    favoritosEncontrados.add(jogo);
                    favoritosRestantes.remove(jogo.getName());
                }
            }
            page++;
        }

        return favoritosEncontrados;
    }

    //Método para retornar lista de games baseada na nota e no gênero
    public List<VideogameModel> recomendarJogosPorGeneroENota(String genero, int notaMinima, int paginas) {
        RestTemplate restTemplate = new RestTemplate();
        List<VideogameModel> recomendados = new ArrayList<>();
        Set<String> nomesAdicionados = new HashSet<>();

        for (int page = 1; page <= paginas; page++) {
            String url = "https://api.rawg.io/api/games?key=" + API_KEY + "&genres=" + genero +
                    "&page=" + page + "&page_size=20&metacritic=" + notaMinima;

            GameEntrada response = restTemplate.getForObject(url, GameEntrada.class);

            if (response != null && response.getResults() != null) {
                for (Game game : response.getResults()) {
                    if (game.getMetacritic() != null && game.getMetacritic() >= notaMinima &&
                            !nomesAdicionados.contains(game.getName())) {

                        VideogameModel model = new VideogameModel();
                        model.setName(game.getName());
                        model.setReleased(game.getReleased());
                        model.setBackground_image(game.getBackground_image());
                        model.setMetacritic(String.valueOf(game.getMetacritic()));

                        if (game.getPlatforms() != null && !game.getPlatforms().isEmpty()) {
                            List<String> plataformas = game.getPlatforms().stream()
                                    .map(p -> p.getPlatform().getName())
                                    .collect(Collectors.toList());
                            model.setPlatforms(plataformas);
                        } else {
                            model.setPlatforms(List.of("Desconhecido"));
                        }


                        if (game.getGenres() != null) {
                            List<String> generos = game.getGenres().stream()
                                    .map(g -> g.getName())
                                    .collect(Collectors.toList());
                            model.setGenres(generos);
                        }

                        recomendados.add(model);
                        nomesAdicionados.add(game.getName());
                    }
                }
            }
        }

        return recomendados;
    }
//Método para retornar informações do projeto
    public void RetornarMembros() {
        System.out.println("integrantes:Alexandre Sartor Teixeira");
        System.out.println("nome_projeto:Catálogo de Games");

    }

}