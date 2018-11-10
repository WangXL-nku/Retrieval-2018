package com.mypro.domain;

public class Movie {
    private int id;
    private String name;

    private String type;
    private String areas;
    private String comment1;
    private String comment2;

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    public String getComment3() {
        return comment3;
    }

    public void setComment3(String comment3) {
        this.comment3 = comment3;
    }

    private String comment3;

    public Movie(int id, String name, String type, String areas, String comment1, String comment2, String comment3, String grade, String actor, String picture, String author, String abst) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.areas = areas;
        this.comment1 = comment1;
        this.comment2 = comment2;
        this.comment3 = comment3;
        this.grade = grade;
        this.actor = actor;
        this.picture = picture;
        this.author = author;
        this.abst = abst;
    }

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

    @Override
    public String toString() {
        return  id +
                " " + name + ' ' +
                " " + type + ' ' +
                " " + areas + ' ' +
                " " + comment1 + ' ' +
                " " + comment2 + ' ' +
                " " + comment3 + ' ' +
                " " + grade + ' ' +
                " " + actor + ' ' +
                "  " + picture + ' ' +
                "  " + author + ' ' +
                " " + abst ;
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
