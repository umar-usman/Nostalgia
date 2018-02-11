package com.example.hp.myapplication;

/**
 * Created by hp on 12/13/2017.
 */

public class Requests {

    public String name;
    public String image;
    public String status;
    public String id;
    public String thumb_image;
    public Requests(){

    }
    public Requests(String id,String name, String image, String status, String thumb_image) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String name) {
        this.id = id;
    }
    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }
}
