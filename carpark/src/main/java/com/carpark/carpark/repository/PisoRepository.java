package com.carpark.carpark.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carpark.carpark.model.Piso;
import com.carpark.carpark.model.TipoVehiculo;

@Repository
public interface PisoRepository extends JpaRepository<Piso, Long> {
    
    Piso findByPiso(int piso);

    Piso findByTipoVehiculoAndDisponiblesGreaterThan(TipoVehiculo tipoVehiculo, int disponibles);

}
