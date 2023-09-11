package com.carpark.carpark.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class TipoVehiculo {
    
    @Id
    @GeneratedValue
    private Long id;

    private String tipoVehiculo;
    private float areaVehiculo;
    private float tarifa;

    @OneToMany(mappedBy = "tipoVehiculo")
    List<Piso> pisos = new ArrayList<>();

    public TipoVehiculo() {
    }

    public TipoVehiculo(String tipoVehiculo, float areaVehiculo, float tarifa) {
        this.tipoVehiculo = tipoVehiculo;
        this.areaVehiculo = areaVehiculo;
        this.tarifa = tarifa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public float getAreaVehiculo() {
        return areaVehiculo;
    }

    public void setAreaVehiculo(float areaVehiculo) {
        this.areaVehiculo = areaVehiculo;
    }

    public float getTarifa() {
        return tarifa;
    }

    public void setTarifa(float tarifa) {
        this.tarifa = tarifa;
    }

    
    
}
