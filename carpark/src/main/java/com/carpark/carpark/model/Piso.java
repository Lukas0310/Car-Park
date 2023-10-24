package com.carpark.carpark.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Piso {
    
    @Id
    @GeneratedValue
    private Long id;

    private int piso;
	private int area;
    private int capacidad = 0;
	private int disponibles = capacidad;

	//llave foranea
	@ManyToOne
	TipoVehiculo tipoVehiculo;

	@OneToMany(mappedBy = "piso")
	List<Vehiculo> vehiculos = new ArrayList<>();

	public Piso() {
	}

	public Piso(int piso, int area, TipoVehiculo tipoVehiculo) {
		this.piso = piso;
		this.area = area;
		this.tipoVehiculo = tipoVehiculo;
		actualizarCapacidad();
		setDisponibles();
	}

	public Piso(int piso, int area) {
		this.piso = piso;
		this.area = area;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPiso() {
		return piso;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public int getCapacidad() {
		actualizarCapacidad();
		return capacidad;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
		actualizarCapacidad();
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}


	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
		actualizarCapacidad();
	}

	public void actualizarCapacidad (){
		capacidad =  (int) (area / tipoVehiculo.getAreaVehiculo());
	}

	public int getDisponibles() {
		actualizarCapacidad();
		return capacidad - vehiculos.size();
	}

	public void setDisponibles(){
		actualizarCapacidad();
		this.disponibles = capacidad - vehiculos.size();
	}
	
}
