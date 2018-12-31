package com.mypro.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordNode implements Serializable {
    private Integer termId;
    private Integer docId;

    public Integer getTermId() {
        return termId;
    }

    public WordNode(Integer termId, Integer docId) {
        this.termId = termId;
        this.docId = docId;
    }

    public WordNode() {
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public Integer getDocId() {
        return docId;
    }

    @Override
    public String toString() {
        return "WordNode{" +
                "termId=" + termId +
                ", docId=" + docId +
                '}';
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }
}
