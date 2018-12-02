package kg.timetotravel.anidea;

public class Idea {
    private String idea;
    private String creationDate;
    private String imageUri = null;

    Idea(String idea, String creationDate, String imageUri){
        this.idea = idea;
        this.creationDate = creationDate;
        this.imageUri = imageUri;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public  String toString(){
        return idea + " " + String.valueOf(creationDate) + " " + imageUri;
    }
}

