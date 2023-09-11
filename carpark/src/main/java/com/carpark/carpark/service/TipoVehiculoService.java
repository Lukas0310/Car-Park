package com.carpark.carpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpark.carpark.model.TipoVehiculo;
import com.carpark.carpark.repository.TipoVehiculoRepository;

@Service
public class TipoVehiculoService {

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    //CRUD de tipo de vehiculo
    //guarda un tipo de vehiculo
    public void agregarTipoVehiculo(TipoVehiculo tipoVehiculo){
        tipoVehiculoRepository.save(tipoVehiculo);
    }

    //borra un tipo de vehiculo con el id
    public void borrarTipoVehiculo(Long id){
        tipoVehiculoRepository.deleteById(id);
    }

    //recupera un tipo de vehiculo con el id
    public TipoVehiculo recuperarTipoVehiculo(Long id){
        return tipoVehiculoRepository.findById(id).get();
    }
    
    //recupera un tipo de vehiculo con el nombre
    public TipoVehiculo recuperarTipoVehiculo(String tipoVehiculo){
        return tipoVehiculoRepository.findByTipoVehiculo(tipoVehiculo);
    }


    //Lista todos los tipos de vehiculo
    public Iterable<TipoVehiculo> listarTipoVehiculo(){
        return tipoVehiculoRepository.findAll();
    }
    
}
