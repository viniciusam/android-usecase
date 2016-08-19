package com.viniciusam.usecase.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Vinicius on 18/08/2016.
 */
public class Post extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String title;

    public Post() {
    }

    public Post(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
