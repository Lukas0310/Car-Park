package com.carpark.carpark.DBCarPark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.carpark.carpark.model.Piso;
import com.carpark.carpark.model.TipoVehiculo;
import com.carpark.carpark.model.Vehiculo;
import com.carpark.carpark.repository.PisoRepository;
import com.carpark.carpark.repository.TipoVehiculoRepository;
import com.carpark.carpark.repository.VehiculoRepository;

@Component
public class DBInit implements CommandLineRunner {
    
    @Autowired
    private PisoRepository pisoRepository;

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    @Autowired
    private VehiculoRepository VehiculoRepository;

    @Override
    public void run(String... args) throws Exception {

        //crea los tipos de vehiculo
        tipoVehiculoRepository.save(new TipoVehiculo("vehiculo", 10, 12));
        tipoVehiculoRepository.save(new TipoVehiculo("camion", 24, 20));
        tipoVehiculoRepository.save(new TipoVehiculo("moto", 2, 5));

        //crea los pisos
        pisoRepository.save(new Piso(1, 160, tipoVehiculoRepository.findByTipoVehiculo("vehiculo")));
        pisoRepository.save(new Piso(2, 192, tipoVehiculoRepository.findByTipoVehiculo("camion")));
        pisoRepository.save(new Piso(3, 100, tipoVehiculoRepository.findByTipoVehiculo("moto")));
       
    }

}
