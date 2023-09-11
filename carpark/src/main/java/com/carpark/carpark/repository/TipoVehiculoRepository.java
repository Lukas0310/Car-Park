package com.carpark.carpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carpark.carpark.model.TipoVehiculo;

@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long>{
    
    TipoVehiculo findByTipoVehiculo(String tipoVehiculo);

}
