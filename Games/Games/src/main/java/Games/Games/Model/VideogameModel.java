 package Games.Games.Model;
 import java.util.List;

 //Classe que representa os campos de características dos jogos da forma
 // que devem ser mostradas ao usuário

 public class VideogameModel {
     private String name;
     private List<String> platforms;
     private String background_image;
     private String metacritic;
     private String released;
     private List<String> genres;


     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public List<String> getPlatforms() {
         return platforms;
     }

     public void setPlatforms(List<String> platforms) {
         this.platforms = platforms;
     }

     public String getBackground_image() {
         return background_image;
     }

     public void setBackground_image(String background_image) {
         this.background_image = background_image;
     }

     public String getMetacritic() {
         return metacritic;
     }

     public void setMetacritic(String metacritic) {
         this.metacritic = metacritic;
     }

     public String getReleased() {
         return released;
     }

     public void setReleased(String released) {
         this.released = released;
     }

     public List<String> getGenres() {
         return genres;
     }

     public void setGenres(List<String> genres) {
         this.genres = genres;
     }
 }
