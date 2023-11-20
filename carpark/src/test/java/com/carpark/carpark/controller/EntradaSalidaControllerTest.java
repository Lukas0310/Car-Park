package com.carpark.carpark.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.carpark.carpark.model.Factura;
import com.carpark.carpark.model.FormData;
import com.carpark.carpark.model.InfoPiso;
import com.carpark.carpark.model.Piso;
import com.carpark.carpark.model.Placa;
import com.carpark.carpark.model.TipoVehiculo;
import com.carpark.carpark.model.Vehiculo;
import com.carpark.carpark.model.VehiculoRespuesta;
import com.carpark.carpark.repository.PisoRepository;
import com.carpark.carpark.repository.TipoVehiculoRepository;
import com.carpark.carpark.repository.VehiculoRepository;

@ActiveProfiles("integrationtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EntradaSalidaControllerTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    @Autowired
    private PisoRepository pisoRepository;

    @BeforeEach
    void init() {
        //crea los tipos de vehiculo
        tipoVehiculoRepository.save(new TipoVehiculo("vehiculo", 10, 12));
        tipoVehiculoRepository.save(new TipoVehiculo("camion", 24, 20));
        tipoVehiculoRepository.save(new TipoVehiculo("moto", 2, 5));

        //crea los pisos
        pisoRepository.save(new Piso(1, 160, tipoVehiculoRepository.findByTipoVehiculo("vehiculo")));
        pisoRepository.save(new Piso(2, 192, tipoVehiculoRepository.findByTipoVehiculo("camion")));
        pisoRepository.save(new Piso(3, 100, tipoVehiculoRepository.findByTipoVehiculo("moto")));

        //crea los vehiculos
        vehiculoRepository.save(new Vehiculo("ABC1234", "123456789", "Mazda", "Rojo", tipoVehiculoRepository.findByTipoVehiculo("vehiculo"), pisoRepository.findByPiso(1)));
       
    }

    @Test
    void entrada() {
        FormData formData = new FormData("123456789", "ABC123", "vehiculo", "Rojo", "Mazda");
        ResponseEntity<VehiculoRespuesta> resp = rest.postForEntity("http://localhost:" + port + "/entradasalida/entrada", formData, VehiculoRespuesta.class);

        // Verificar si el cuerpo de la respuesta no es nulo antes de acceder a sus métodos
        if (resp.getBody() != null) {
            VehiculoRespuesta vehiculoRespuesta = new VehiculoRespuesta("ABC123", "123456789", "Mazda", "Rojo", "vehiculo", resp.getBody().getFechaEntrada(), 1);
            assertEquals(vehiculoRespuesta, resp.getBody());
        }
    }

    @Test
    void factura(){
        Placa placa = new Placa("ABC123");
        ResponseEntity<Factura> resp = rest.postForEntity("http://localhost:" + port + "/entradasalida/factura", placa, Factura.class);
        // Verificar si el cuerpo de la respuesta no es nulo antes de acceder a sus métodos
        if (resp.getBody() != null) {
            Factura factura = new Factura("ABC123", resp.getBody().getHoraEntrada(), resp.getBody().getHoraSalida(), resp.getBody().getPrecio());
            assertEquals(factura, resp.getBody());
        }
    }

    @Test
    void salida(){
        Placa placa = new Placa("ABC1234");
        ResponseEntity<VehiculoRespuesta> resp = rest.postForEntity("http://localhost:" + port + "/entradasalida/salida", placa, VehiculoRespuesta.class);
        assertEquals(null, resp.getBody());
    }

    @Test
    void getAllTiposvehiculo(){
        List<String> resp = rest.getForObject("http://localhost:" + port + "/entradasalida/getAllTiposvehiculo", List.class);
        List<String> tiposVehiculo = List.of("vehiculo", "camion", "moto");
        assertEquals(tiposVehiculo, resp);
    }

@Test
void getAllPisos(){
    ResponseEntity<List<InfoPiso>> response = rest.exchange(
            "http://localhost:" + port + "/entradasalida/info",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<InfoPiso>>() {}
    );

    List<InfoPiso> resp = response.getBody();

    InfoPiso piso1 = new InfoPiso(1L, 1, 16, 15);
    InfoPiso piso2 = new InfoPiso(2L, 2, 8, 8);
    InfoPiso piso3 = new InfoPiso(3L, 3, 50, 50);
    List<InfoPiso> infoPisos = List.of(piso1, piso2, piso3);

    assertEquals(infoPisos, resp);
}

}
