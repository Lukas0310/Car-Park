package com.carpark.carpark.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carpark.carpark.model.Factura;
import com.carpark.carpark.model.FormData;
import com.carpark.carpark.model.InfoPiso;
import com.carpark.carpark.model.Piso;
import com.carpark.carpark.model.Placa;
import com.carpark.carpark.model.TipoVehiculo;
import com.carpark.carpark.model.Vehiculo;
import com.carpark.carpark.model.VehiculoRespuesta;
import com.carpark.carpark.service.EntradaSalidaService;
import com.carpark.carpark.service.PisoService;
import com.carpark.carpark.service.TipoVehiculoService;

@RestController
@RequestMapping("/entradasalida")
public class EntradaSalidaControler {

    @Autowired
    private EntradaSalidaService entradaSalidaService;

    @Autowired
    private TipoVehiculoService tipoVehiculoService;

    @Autowired
    private PisoService pisoService;

    // registra una entrada
    @PostMapping("/entrada")
    @CrossOrigin("http://localhost:4200/") 
    public ResponseEntity<VehiculoRespuesta> registrarEntrada(@RequestBody FormData formData) {

        HttpHeaders responseHeaders = new HttpHeaders();
        
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdConductor(formData.idConductor);
        vehiculo.setPlaca(formData.placa);
        vehiculo.setColor(formData.color);
        vehiculo.setMarca(formData.marca);
        vehiculo.setTipoVehiculo(tipoVehiculoService.recuperarTipoVehiculo(formData.tipo));
        Piso piso = pisoService.recuperarPisoDisponible(vehiculo.getTipoVehiculo());
        // Si no hay pisos disponibles, devuelve un codigo en el header = -1 que dice que no hay espacios disponibles
        if (piso == null) {
            responseHeaders.set("Espacio", "lleno");
            return ResponseEntity.status(409).headers(responseHeaders).body(null);
        }
        vehiculo.setPiso(piso);
        piso.setDisponibles();
        vehiculo.setFechaEntrada();

        // Registra la entrada
        entradaSalidaService.guardarVehiculo(vehiculo);

        // guarda los datos del vehiculo en un objeto de VehicleRespuesta
        VehiculoRespuesta vehiculoRespuesta = new VehiculoRespuesta();
        vehiculoRespuesta.placa = vehiculo.getPlaca();
        vehiculoRespuesta.idConductor = vehiculo.getIdConductor();
        vehiculoRespuesta.marca = vehiculo.getMarca();
        vehiculoRespuesta.color = vehiculo.getColor();
        vehiculoRespuesta.tipo = vehiculo.getTipoVehiculo().getTipoVehiculo();
        vehiculoRespuesta.fechaEntrada = vehiculo.getFechaEntrada();
        vehiculoRespuesta.piso = vehiculo.getPiso().getPiso();

        // Devuelve un responseHeader = 1 con el formData
        responseHeaders.set("Espacio", "disponible");
        return ResponseEntity.ok().headers(responseHeaders).body(vehiculoRespuesta);

    }

    // retorna la factura de un vehiculo
    @PostMapping("/factura")
    @CrossOrigin("http://localhost:4200/")
    public ResponseEntity<Factura> getFactura(@RequestBody Placa placa){
            
            HttpHeaders responseHeaders = new HttpHeaders();

            // Recupera el vehiculo
            Vehiculo vehiculo = entradaSalidaService.recuperarVehiculo(placa.placa); 

            if (vehiculo == null) {
                responseHeaders.set("Vehiculo", "no existe");
                return ResponseEntity.status(409).headers(responseHeaders).body(null);
            }

            // Si el vehiculo existe, crea una factura
            Factura factura = new Factura();
            factura.placa = vehiculo.getPlaca();
            factura.horaEntrada = vehiculo.getFechaEntrada();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            factura.horaSalida = LocalDateTime.now().format(formato);
            factura.precio = vehiculo.getValorAPagar();

            return ResponseEntity.ok().headers(responseHeaders).body(factura);

    }

    // elimina un vehiculo
    @PostMapping("/salida")
    @CrossOrigin("http://localhost:4200/")
    public ResponseEntity<VehiculoRespuesta> registrarSalida(@RequestBody Placa placa) {

        HttpHeaders responseHeaders = new HttpHeaders();

        // Recupera el vehiculo
        Vehiculo vehiculo = entradaSalidaService.recuperarVehiculo(placa.placa); 

        if (vehiculo == null) {
            responseHeaders.set("Vehiculo", "no existe");
            return ResponseEntity.status(409).headers(responseHeaders).body(null);
        }

        // Si el vehiculo existe, elimina el vehiculo
        entradaSalidaService.borrarVehiculo(vehiculo);
       
        //responseHeaders.set("Vehiculo", "existe");
        return ResponseEntity.ok().headers(responseHeaders).body(null);

    }

    @GetMapping("/getAllTiposvehiculo")
    @CrossOrigin("http://localhost:4200/")
    public List<String> getAll(){

        // Obtener la lista de tipos de vehículo
        Iterable<TipoVehiculo> tipos = tipoVehiculoService.listarTipoVehiculo();

        // Crear una lista para almacenar los nombres de los tipos de vehículo
        List<String> tiposString = new ArrayList<>();

        // Recorrer la lista de tipos y convertirlos a cadenas
        for (TipoVehiculo tipo : tipos) {
            tiposString.add(tipo.getTipoVehiculo());
        }

        return tiposString;
    }
    
    // devuelve la informacion de todos los pisos
    @GetMapping("/info")
    @CrossOrigin("http://localhost:4200/")
    public List<InfoPiso> infoPisos() {
        
        List<InfoPiso> infoPisos = new ArrayList<InfoPiso>();
        List<Piso> pisos = pisoService.listarPisos();
        for (Piso piso : pisos) {
            infoPisos.add(new InfoPiso(piso.getId(), piso.getPiso(), piso.getCapacidad(), piso.getDisponibles()));
        }
        return infoPisos;
    }
    
}
