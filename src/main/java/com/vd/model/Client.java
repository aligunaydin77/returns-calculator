package com.vd.model;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by alis on 09/06/2017.
 */
public class Client {

    @Id
    private String id;

    private String name;

    List<Investment> listOfInvestment;

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
