package com.carpark.carpark.model;

public class InfoPiso {
    
    Long id;
    int numPiso;
    int capacidad;
    int libres;

    public InfoPiso() {
    }

    public InfoPiso(Long id, int numPiso, int capacidad, int libres) {
        this.id = id;
        this.numPiso = numPiso;
        this.capacidad = capacidad;
        this.libres = libres;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + numPiso;
        result = prime * result + capacidad;
        result = prime * result + libres;
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
        InfoPiso other = (InfoPiso) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (numPiso != other.numPiso)
            return false;
        if (capacidad != other.capacidad)
            return false;
        if (libres != other.libres)
            return false;
        return true;
    }

    

    @Override
    public String toString() {
        return "InfoPiso [id=" + id + ", numPiso=" + numPiso + ", capacidad=" + capacidad + ", libres=" + libres + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumPiso() {
        return numPiso;
    }

    public void setNumPiso(int numPiso) {
        this.numPiso = numPiso;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getLibres() {
        return libres;
    }

    public void setLibres(int libres) {
        this.libres = libres;
    }

}
