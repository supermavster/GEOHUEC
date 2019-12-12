package com.cali.geohole.model;

public class User {
    public String placa;
    public String cedula;

    public User(String placa, String cedula) {
        this.placa = placa;
        this.cedula = cedula;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
