package com.carpark.carpark.DBCarPark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.carpark.carpark.model.Piso;
import com.carpark.carpark.model.TipoVehiculo;
import com.carpark.carpark.repository.PisoRepository;
import com.carpark.carpark.repository.TipoVehiculoRepository;

import jakarta.transaction.Transactional;

@Configuration
@Profile({"default"})
public class DBInit implements CommandLineRunner {
    
    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    @Autowired
    private PisoRepository pisoRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        tipoVehiculoRepository.save(new TipoVehiculo("vehiculo", 10, 12));
        tipoVehiculoRepository.save(new TipoVehiculo("camion", 24, 20));
        tipoVehiculoRepository.save(new TipoVehiculo("moto", 2, 5));
        
        pisoRepository.save(new Piso(1, 160, tipoVehiculoRepository.findByTipoVehiculo("vehiculo")));
        pisoRepository.save(new Piso(2, 192, tipoVehiculoRepository.findByTipoVehiculo("camion")));
        pisoRepository.save(new Piso(3, 100, tipoVehiculoRepository.findByTipoVehiculo("moto")));
       
    }

}
