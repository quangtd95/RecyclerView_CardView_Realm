package td.quang.danhbamenurycyclerviewrealm.model;

import io.realm.RealmObject;

/**
 * Created by Quang_TD on 11/10/2016.
 */
public class Contact extends RealmObject{

    private String imageurl;
    private String name;
    private String number;

    public Contact(){

    }

    public Contact(String imageurl, String name, String number) {
        this.imageurl = imageurl;
        this.name = name;
        this.number = number;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
