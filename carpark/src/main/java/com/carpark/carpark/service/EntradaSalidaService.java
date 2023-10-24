package com.carpark.carpark.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpark.carpark.model.Vehiculo;
import com.carpark.carpark.repository.VehiculoRepository;

@Service
public class EntradaSalidaService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // guarda un vehiculo en la base de datos
    public void guardarVehiculo(Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
    }

    // borrado de un vehiculo de la base de datos
    public void borrarVehiculo(Vehiculo vehiculo) {
        vehiculoRepository.delete(vehiculo);
    }

    // recupera un vehiculo de la base de datos por su placa
    public Vehiculo recuperarVehiculo(String placa) {
        return vehiculoRepository.findByPlaca(placa);
    }


}
