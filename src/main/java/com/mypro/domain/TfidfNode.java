package com.mypro.domain;

import java.io.Serializable;

public class TfidfNode implements Serializable {
    public Integer wordId;
    public Double number;

    public TfidfNode(Double number) {
        this.number = number;
    }

    public TfidfNode(Integer wordId) {
        this.wordId = wordId;
    }

    public TfidfNode() {
    }

    public TfidfNode(Integer wordId, Double number) {
        this.wordId = wordId;
        this.number = number;
    }
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (this == obj) {
            return true;
        }
        if (obj != null && obj instanceof TfidfNode) {
            TfidfNode a = (TfidfNode) obj;
            return (this.wordId == null ? a.wordId == null : this.wordId.equals(a.wordId));// 多个域比较范式
        }
        return false;
    }

    @Override
    public String toString() {
        return "TfidfNode{" +
                "wordId='" + wordId + '\'' +
                ", number=" + number +
                '}';
    }
}
