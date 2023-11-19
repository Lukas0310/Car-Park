package com.carpark.carpark.model;

public class VehiculoRespuesta {
    public String placa;
    public String idConductor;
    public String marca;
    public String color;
    public String tipo;
    public String fechaEntrada;
    public int piso;

    public VehiculoRespuesta(String placa, String idConductor, String marca, String color, String tipo,
            String fechaEntrada, int piso) {
        this.placa = placa;
        this.idConductor = idConductor;
        this.marca = marca;
        this.color = color;
        this.tipo = tipo;
        this.fechaEntrada = fechaEntrada;
        this.piso = piso;
    }

    public String getPlaca() {
        return placa;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((placa == null) ? 0 : placa.hashCode());
        result = prime * result + ((idConductor == null) ? 0 : idConductor.hashCode());
        result = prime * result + ((marca == null) ? 0 : marca.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + ((fechaEntrada == null) ? 0 : fechaEntrada.hashCode());
        result = prime * result + piso;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VehiculoRespuesta other = (VehiculoRespuesta) obj;
        if (placa == null) {
            if (other.placa != null)
                return false;
        } else if (!placa.equals(other.placa))
            return false;
        if (idConductor == null) {
            if (other.idConductor != null)
                return false;
        } else if (!idConductor.equals(other.idConductor))
            return false;
        if (marca == null) {
            if (other.marca != null)
                return false;
        } else if (!marca.equals(other.marca))
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (fechaEntrada == null) {
            if (other.fechaEntrada != null)
                return false;
        } else if (!fechaEntrada.equals(other.fechaEntrada))
            return false;
        if (piso != other.piso)
            return false;
        return true;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(String idConductor) {
        this.idConductor = idConductor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public VehiculoRespuesta(){

    }
}
