package com.cali.geohole.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    public String placa;
    public String cedula;
    public ArrayList<Hole> holes = null;


    public User(String placa, String cedula) {
        this.placa = placa;
        this.cedula = cedula;
        holes = new ArrayList<Hole>();
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

    public void addGeoData(Hole hole) {
        holes.add(hole);
    }

    public ArrayList<Hole> getHoles() {
        return holes;
    }

    public void setHoles(ArrayList<Hole> holes) {
        this.holes = holes;
    }
}
