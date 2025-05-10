package Games.Games.util;

import java.util.List;

//Classe que receberá os dados da Api vindo em JSON
public class GameEntrada {
    private List<Game> results;

    public List<Game> getResults() {
        return results;
    }

    public void setResults(List<Game> results) {
        this.results = results;
    }
}

