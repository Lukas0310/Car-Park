package com.carpark.carpark.model;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Vehiculo {

    @Id
    @GeneratedValue
    private Long id;

    private String placa;
    private String idConductor;
    private String marca;
    private String color;
    private String fechaEntrada;

    //llaves foranea
    @ManyToOne
    TipoVehiculo tipoVehiculo;
    
    @ManyToOne
    Piso piso;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String idConductor, String marca, String color, TipoVehiculo tipoVehiculo, Piso piso) {
        this.placa = placa;
        this.idConductor = idConductor;
        this.marca = marca;
        this.color = color;
        this.tipoVehiculo = tipoVehiculo;
        this.piso = piso;
        setFechaEntrada();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
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

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Piso getPiso() {
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    // establece la fecha de entrada. el formato de la fecha es "yyyy-MM-dd HH:mm:ss"
    public void setFechaEntrada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fecha = LocalDateTime.now();
        this.fechaEntrada = fecha.format(formato);        
    }

    // retorna el tiempo en minutos en el parqueadero. el formato de la fecha es "yyyy-MM-dd HH:mm:ss"
    public int getTiempoTranscurrido () {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fecha = LocalDateTime.now();
        LocalDateTime fechaEntrada = LocalDateTime.parse(this.fechaEntrada, formato);
        Duration duracion = Duration.between(fechaEntrada, fecha);
        return (int) duracion.toMinutes();
    }

    // retorna el valor a pagar por el tiempo transcurrido en el parqueadero
    public int getValorAPagar () {
        int tiempo = getTiempoTranscurrido();
        int total = (int) getTipoVehiculo().getTarifa() * tiempo;
        return total;
    }
    
}