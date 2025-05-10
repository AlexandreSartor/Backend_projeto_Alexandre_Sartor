package Games.Games.util;

import java.util.List;

//Classe que representa a estrutura original dos dados recebidos da Api,mas
// filtra as informações dos games que serão exibidas ao usuário

public class Game {
    private String name;
    private String background_image;
    private Integer metacritic;
    private String released;
    private List<Platformcaixa> platforms;
    private List<Genre> genres;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public Integer getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(Integer metacritic) {
        this.metacritic = metacritic;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public List<Platformcaixa> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platformcaixa> platforms) {
        this.platforms = platforms;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    // Classe interna para o gênero
    public static class Genre {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }



    }
}