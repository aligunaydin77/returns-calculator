package com.vd.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Client {

    @Id
    private String id;

    private String name;


    List<Investment> listOfInvestment;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Investment> getListOfInvestment() {
        return listOfInvestment;
    }

    public void setListOfInvestment(List<Investment> listOfInvestment) {
        this.listOfInvestment = listOfInvestment;
    }
}
