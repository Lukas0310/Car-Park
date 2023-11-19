package com.carpark.carpark.model;

public class FormData {

    public String idConductor;
    public String placa;
    public String tipo;
    public String color;
    public String marca;
    
    public FormData() {
    }
    
    public FormData(String idConductor, String placa, String tipo, String color, String marca) {
        this.idConductor = idConductor;
        this.placa = placa;
        this.tipo = tipo;
        this.color = color;
        this.marca = marca;
    }
    public String getIdConductor() {
        return idConductor;
    }
    public void setIdConductor(String idConductor) {
        this.idConductor = idConductor;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

}
