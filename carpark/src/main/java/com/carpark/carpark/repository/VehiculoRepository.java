package com.carpark.carpark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carpark.carpark.model.Piso;
import com.carpark.carpark.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>{
    
    Vehiculo findByPlaca(String placa);

    List<Vehiculo> findAllByPiso(Piso piso);

}
