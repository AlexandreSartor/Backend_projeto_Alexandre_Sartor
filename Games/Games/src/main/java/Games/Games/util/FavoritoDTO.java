package Games.Games.util;


//Objeto de transferência de dados usado para receber o nome do jogo
//favorito enviado pelo cliente via requisição POST.


public class FavoritoDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

