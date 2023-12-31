package com.carpark.carpark.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpark.carpark.model.Piso;
import com.carpark.carpark.model.TipoVehiculo;
import com.carpark.carpark.repository.PisoRepository;

@Service
public class PisoService {
    
    @Autowired
    private PisoRepository pisoRepository;

    //lista todos los pisos
    public List<Piso> listarPisos(){
        return pisoRepository.findAll();
    }

    //agrega un piso
    public void agregarPiso(Piso piso){
        pisoRepository.save(piso);
    }
    
    //borra un piso con el id
    public void borrarPiso(Long id){
        pisoRepository.deleteById(id);
    }

    //recupera un piso con el id
    public Piso recuperarPiso(Long id){
        return pisoRepository.findById(id).get();
    }

    //dado un tipo de vehiculo, retorna un piso que tenga espacios disponibles
    public Piso recuperarPisoDisponible(TipoVehiculo tipoVehiculo){
        List<Piso> pisos = pisoRepository.findAll();
        for (Piso piso : pisos) {
            if(piso.getTipoVehiculo().getTipoVehiculo().equals(tipoVehiculo.getTipoVehiculo()) && piso.getDisponibles() > 0){
                return piso;
            }
        }
        return null;
    }

}
