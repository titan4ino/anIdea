package kg.timetotravel.anidea;

public class Comments {

    private String body;
    private String name;
    private String imageUrl;


    Comments(String _body, String _name, String _imageUrl) {
        body = _body;
        name = _name;
        imageUrl = _imageUrl;
    }


    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
