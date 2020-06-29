package com.example.myconsume.entiy;


import org.litepal.crud.DataSupport;

/**
 * 账单分类bean
 */

public class BSort extends DataSupport {


    private int id;
    private String sortName;
    private String sortImg;

    private int priority;

    private float cost;
    private Boolean income;


    public BSort() {
    }

    public BSort(String sortName, String sortImg, int priority, float cost,
                 Boolean income) {
        this.sortName = sortName;
        this.sortImg = sortImg;
        this.priority = priority;
        this.cost = cost;
        this.income = income;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortImg() {
        return sortImg;
    }

    public void setSortImg(String sortImg) {
        this.sortImg = sortImg;
    }

    public Boolean getIncome() {
        return income;
    }

    public void setIncome(Boolean income) {
        this.income = income;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int order) {
        this.priority = order;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}