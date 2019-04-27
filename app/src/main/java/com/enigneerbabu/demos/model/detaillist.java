package com.enigneerbabu.demos.model;

public class detaillist {

    public String name;
    public int id;
    public int postId;
    public String email;
    public String body;

    public detaillist() {

    }

    public detaillist(String name, int id, int postId,String email,String body) {
        this.name = name;
        this.id = id;
        this.postId = postId;
        this.email = email;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}