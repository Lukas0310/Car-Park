package com.carpark.carpark.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.carpark.carpark.model.Piso;
import com.carpark.carpark.model.TipoVehiculo;
import com.carpark.carpark.model.Vehiculo;
import com.carpark.carpark.repository.PisoRepository;
import com.carpark.carpark.repository.TipoVehiculoRepository;
import com.carpark.carpark.repository.VehiculoRepository;

@ActiveProfiles("systemtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class EntradaSalidaSystemTest {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    @Autowired
    private PisoRepository pisoRepository;

    String baseUrl;
    private ChromeDriver driver;
    private WebDriverWait wait;

    

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

        this.baseUrl = "http://localhost:4200";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        // se establece la ruta del driver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lukas\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        this.driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    }

    @AfterEach
    void end() {
        // driver.close();
        driver.quit();
    }

    void entrada1(){
        driver.get(baseUrl + "/entrada");
        WebElement idConductor = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idConductor")));
        idConductor.sendKeys("id123");
        WebElement placa = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("placa")));
        placa.sendKeys("ABC123");
        WebElement tipo = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tipo")));
        tipo.sendKeys("vehiculo");
        WebElement color = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("color")));
        color.sendKeys("rojo");
        WebElement marca = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("marca")));
        marca.sendKeys("mazda");
        WebElement submit = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idSubmit")));
        submit.click();
        WebElement exito = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("exito")));
        WebElement valorPiso = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("valorPiso")));
        String piso = valorPiso.getText();
        if (!piso.equals("1")) {
            fail("El piso no es el esperado");
        }
        WebElement valorPlaca = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("valorPlaca")));
        String placaTexto = valorPlaca.getText();
        if (!placaTexto.equals("ABC123")) {
            fail("La placa no es la esperada");
        }
    }
    
    void salida1(){
        driver.get(baseUrl + "/salida");
        WebElement placa = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("placa")));
        placa.sendKeys("ABC1234");
        WebElement submit = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idSubmit")));
        submit.click();
        WebElement exito = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("exito")));
        WebElement valorPlaca = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idPlaca")));
        String placaTexto = valorPlaca.getText();
        if (!placaTexto.equals("ABC1234")) {
            fail("La placa no es la esperada");
        }
        WebElement idSubmitSalir = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idSubmitSalir")));
        idSubmitSalir.click();
        WebElement valorMensaje = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("valorMensaje")));
        String mensaje = valorMensaje.getText();
        if (!mensaje.equals("Salida exitosa. Placa: ABC1234")) {
            fail("El mensaje no es el esperado");
        }
    }

    @Test
    void entrada() {
        entrada1();
    }

    @Test
    void salida() {
        salida1();
    }

    @Test
    void actualizacionEspaciosEntrada() {
        entrada1();
        driver.get(baseUrl + "/disponibles");
        // verificar que en la tabla con id idTabla, el valor de disponibles en la fila 1 se igual a la Capacidad - 2
        WebElement disponibles = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"idTabla\"]/tbody/tr[1]/td[4]")));
        String disponiblesTexto = disponibles.getText();
        WebElement capacidad = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"idTabla\"]/tbody/tr[1]/td[3]")));
        String capacidadTexto = capacidad.getText();
        if (!disponiblesTexto.equals(String.valueOf(Integer.parseInt(capacidadTexto) - 2))) {
            fail("El valor de disponibles no es el esperado");
        }
    }

    @Test
    void actualizacionEspaciosSalida() {
        salida1();
        driver.get(baseUrl + "/disponibles");
        // verificar que en la tabla con id idTabla, el valor de disponibles en la fila 1 se igual a la Capacidad
        WebElement disponibles = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"idTabla\"]/tbody/tr[1]/td[4]")));
        String disponiblesTexto = disponibles.getText();
        WebElement capacidad = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"idTabla\"]/tbody/tr[1]/td[3]")));
        String capacidadTexto = capacidad.getText();
        if (!disponiblesTexto.equals(capacidadTexto)) {
            fail("El valor de disponibles no es el esperado");
        }
    }

    @Test
    void crearPiso(){
        driver.get("http://localhost:8080/pisos/mostrar");
        WebElement operacion = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("operacion")));
        operacion.sendKeys("crear");
        WebElement numPiso = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id")));
        numPiso.sendKeys("4");
        WebElement tipoVehiculo = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tipoVehiculo")));
        tipoVehiculo.sendKeys("vehiculo");
        WebElement area = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("edad")));
        area.sendKeys("100");
        WebElement submit = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idSubmitCrearPiso")));
        submit.click();
        // verificar los valores correspondientes en la tabla
        WebElement id = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"idTablaPisos\"]/tbody/tr[5]/td[2]")));
        String idTexto = id.getText();
        if (!idTexto.equals("4")) {
            fail("El id no es el esperado");
        }
        WebElement piso = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"idTablaPisos\"]/tbody/tr[5]/td[3]")));
        String pisoTexto = piso.getText();
        if (!pisoTexto.equals("vehiculo")) {
            fail("El piso no es el esperado");
        }
        WebElement tipo = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"idTablaPisos\"]/tbody/tr[5]/td[5]")));
        String tipoTexto = tipo.getText();
        if (!tipoTexto.equals("100")) {
            fail("El tipo no es el esperado");
        }
    }

}

/*
 * <div class="profile-section">
        <h1 class="profile-title">Pisos</h1>
        <div class="content-wrapper">
            <!-- Tabla de Usuarios -->
            <table class="user-table">
                <tr>
                    <th>ID</th>
                    <th>Piso</th>
                    <th>Tipo</th>
                    <th>Capacidad</th>
                    <th>Area</th>
                </tr>
                <tr th:each="piso : ${pisos}">
                    <td th:text="${piso.id}"></td>
                    <td th:text="${piso.piso}"></td>
                    <td th:text="${piso.tipoVehiculo.tipoVehiculo}"></td>
                    <td th:text="${piso.getCapacidad}"></td>
                    <td th:text="${piso.area}"></td>
                </tr>
            </table>
            <!-- Línea vertical -->
            <div class="vertical-line"></div>
            <div id="Form-Wrappper">
                <!-- Menú desplegable para seleccionar la operación -->
                <label for="operacion">Selecciona una operación:</label>
                <select id="operacion">
                    <option value="crear">Crear</option>
                    <option value="borrar">Borrar</option>
                    <option value="modificar">Modificar</option>
                </select>
    
                <!-- Contenedor principal con fondo gris claro -->
                <div class="contenedor-principal">
                    <!-- Contenedor para el formulario de Crear Usuario -->
                    <div id="crearUsuario" style="display: none;">
                        <h2>Crear piso</h2>
                        <form action="agregar" method="POST">
                            <div class="campo">
                                <label for="id">Numero del piso:</label>
                                <input type="text" id="id" name="numPiso">
                            </div>
    
                            <!--lista de tipos de vehiculos-->
                            <div class="campo">
                                <label for="tipoVehiculo">Tipo de vehiculo:</label>
                                <select id="tipoVehiculo" name="tipoVehiculo">
                                    <option value=""></option>"
                                    <th:block th:each="tipoVehiculo : ${tipoVehiculos}">
                                        <option th:value="${tipoVehiculo.tipoVehiculo}" th:text="${tipoVehiculo.tipoVehiculo}"></option>
                                    </th:block>
                                </select>
                            </div>
    
                            <div class="campo">
                                <label for="edad">Area:</label>
                                <input type="text" id="edad" name="area">
                            </div>
    
                            <input type="submit" value="Aceptar">
                        </form>
                    </div>
    
                    <!-- Contenedor para el formulario de Borrar Usuario -->
                    <div id="borrarUsuario" style="display: none;">
                        <h2>Borrar piso</h2>
                        <form action="borrar" method="POST">
                            <div class="campo">
                                <label for="idBorrar">ID del piso:</label>
                                <input type="text" id="idBorrar" name="idBorrar">
                            </div>
    
                            <input type="submit" value="Aceptar">
                        </form>
                    </div>
    
                    <!-- Contenedor para el formulario de Modificar Usuario -->
                    <div id="modificarUsuario" style="display: none;">
                        <h2>Modificar piso</h2>
                        <form id="modificarPiso" action="modificar" method="POST">
                            <div class="campo">
                                <label for="idPiso">Id del piso:</label>
                                <input type="text" id="idPiso" name="idPiso">
                            </div>
                            <div class="campo">
                                <label for="numPiso">Numero del piso:</label>
                                <input type="text" id="numPiso" name="numPiso">
                            </div>
    
                            <!--lista de tipos de vehiculos-->
                            <div class="campo">
                                <label for="tipoVehiculo">Tipo de vehiculo:</label>
                                <select id="tipoVehiculo" name="tipoVehiculo">
                                    <option value=""></option>"
                                    <th:block th:each="tipoVehiculo : ${tipoVehiculos}">
                                        <option th:value="${tipoVehiculo.tipoVehiculo}" th:text="${tipoVehiculo.tipoVehiculo}"></option>
                                    </th:block>
                                </select>
                            </div>
    
                            <div class="campo">
                                <label for="capacidad">Area:</label>
                                <input type="text" id="capacidad" name="area">
                            </div>
    
                            <input type="submit" value="Aceptar">
                        </form>
                    </div>
                </div>        
            </div>
        </div>
    </div>
    <script>
        //funcion para cambiar el contenido de acuerdo al elemento seleccionado
        function cambiarContenidoFormulario(){
            // Obtener elementos HTML
            var operacionSelect = document.getElementById("operacion");
            var crearUsuarioDiv = document.getElementById("crearUsuario");
            var borrarUsuarioDiv = document.getElementById("borrarUsuario");
            var modificarUsuarioDiv = document.getElementById("modificarUsuario");
            crearUsuarioDiv.style.display = "block";


            // Evento de cambio en la selección de operación
            operacionSelect.addEventListener("change", function () {
                // Ocultar todos los formularios
                crearUsuarioDiv.style.display = "none";
                borrarUsuarioDiv.style.display = "none";
                modificarUsuarioDiv.style.display = "none";

                // Mostrar el formulario correspondiente a la operación seleccionada
                var operacionSeleccionada = operacionSelect.value;
                if (operacionSeleccionada === "crear") {
                    crearUsuarioDiv.style.display = "block";
                } else if (operacionSeleccionada === "borrar") {
                    borrarUsuarioDiv.style.display = "block";
                } else if (operacionSeleccionada === "modificar") {
                    modificarUsuarioDiv.style.display = "block";
                }
            });    
        }
        
        window.onload = function(){
            cambiarContenidoFormulario();
            defaultValuesModificar();
        }
    </script>
 */