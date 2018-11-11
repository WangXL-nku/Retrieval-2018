package com.mypro.domain;

public class Movie {
    private int id;
    private String name;

    private String type;
    private String areas;

    public Movie(int id, String name, String type, String areas, String grade, String actor, String picture, String author, String abst) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.areas = areas;
        this.grade = grade;
        this.actor = actor;
        this.picture = picture;
        this.author = author;
        this.abst = abst;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    private String grade;
    private String actor;
    private String picture;

    public int getId() {
        return id;
    }

    public Movie() {
    }
    public Movie(int id, String name, String author, String abst) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.abst = abst;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    private String author;
    private String abst;
}
