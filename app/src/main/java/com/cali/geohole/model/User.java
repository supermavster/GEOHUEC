package com.cali.geohole.model;

import java.io.Serializable;

public class User implements Serializable {
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
