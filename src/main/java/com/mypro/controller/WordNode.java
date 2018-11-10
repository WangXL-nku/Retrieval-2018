package com.mypro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordNode {
    public String getName() {
        return name;
    }

    public WordNode() {
        this.infoList = new ArrayList<WordTextInfoNode>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public WordNode(String name, List<WordTextInfoNode> infoList) {
        this.name = name;
        this.infoList = infoList;
    }

    public List<WordTextInfoNode> getInfoList() {
        return infoList;
    }

    public WordNode(String name) {
        this.name = name;
        this.infoList = new ArrayList<WordTextInfoNode>();
    }

    public void setInfoList(List<WordTextInfoNode> infoList) {
        this.infoList = infoList;
    }

    private String name;
    private List<WordTextInfoNode> infoList;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordNode wordo = (WordNode) o;
        return Objects.equals(name, wordo.name);
    }

}
