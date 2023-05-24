package com.imdachillo.associationrules.models;

import java.util.*;

public class Transaction {
    private List<String> articles;
    private int support;

    public Transaction(List<String> articles, int support) {
        this.articles = articles;
        this.support = support;
    }
    public Transaction(){
        this.setArticles(new ArrayList<>());
    }

    public Transaction(List<String> articles) {
        this.articles = articles;
    }

    public List<String> getArticles() {
        return articles;
    }

    public void setArticles(List<String> articles) {
        this.articles = articles;
    }


    public int getSupport() {
        return support;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "article=" + articles +
                ", support=" + support +
                '}';
    }

    public void setSupport(int support) {
        this.support = support;
    }


    /**
     * if the articles in the transaction argument contains all articles that are exist in this class
     * return true
     *
     *
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return getArticles().equals(that.getArticles());
    }
    public boolean equalsC(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return getArticles().containsAll(that.getArticles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticles());
    }
}

