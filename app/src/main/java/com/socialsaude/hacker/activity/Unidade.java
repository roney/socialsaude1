package com.socialsaude.hacker.activity;


public class Unidade {

    private String name;
    private String address;
    private String expedient;

    public Unidade(String name, String address, String expedient) {
        this.name = name;
        this.address = address;
        this.expedient = expedient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }
}
