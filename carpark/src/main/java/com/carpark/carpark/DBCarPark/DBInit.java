package com.carpark.carpark.DBCarPark;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {

        //crea los tipos de vehiculo
        //tipoVehiculoRepository.save(new TipoVehiculo("vehiculo", 10, 12));
        //tipoVehiculoRepository.save(new TipoVehiculo("camion", 24, 20));
        //tipoVehiculoRepository.save(new TipoVehiculo("moto", 2, 5));
        
        //crea los pisos
        //pisoRepository.save(new Piso(1, 160, tipoVehiculoRepository.findByTipoVehiculo("vehiculo")));
        //pisoRepository.save(new Piso(2, 192, tipoVehiculoRepository.findByTipoVehiculo("camion")));
        //pisoRepository.save(new Piso(3, 100, tipoVehiculoRepository.findByTipoVehiculo("moto")));
       
    }

}
