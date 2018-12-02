package kg.timetotravel.anidea;

import com.orm.SugarRecord;

public class Comments extends SugarRecord<Comments>{

    private String body;
    private String name;
    private String imageUrl;

    public Comments(){}

    public Comments(String _body, String _name, String _imageUrl) {
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
