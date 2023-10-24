package com.carpark.carpark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carpark.carpark.model.Piso;
import com.carpark.carpark.model.TipoVehiculo;
import com.carpark.carpark.service.PisoService;
import com.carpark.carpark.service.TipoVehiculoService;

@Controller
@RequestMapping("/tipoVehiculo")
public class TipoVehiculoController {
    
    @Autowired
    private TipoVehiculoService tipoVehiculoService;

    @Autowired
    private PisoService pisoService;

    //CRUD de tipo de vehiculo

    //Crea un tipo de vehiculo
    @PostMapping("/crear")
    public String crearTipoVehiculo(@RequestParam("tipoVehiculoNombre") String tipoVehiculoNombre, 
                                    @RequestParam("areaVehiculo") float areaVehiculo,
                                    @RequestParam("tarifa") float tarifa){
        tipoVehiculoService.agregarTipoVehiculo(new TipoVehiculo(tipoVehiculoNombre, areaVehiculo, tarifa));
        return "redirect:/tipoVehiculo/mostrar";
    }

    //Borra un tipo de vehiculo
    @PostMapping("/borrar")
    public String borrarTipoVehiculo(@RequestParam("id") Long id){
        tipoVehiculoService.borrarTipoVehiculo(id);
        return "redirect:/tipoVehiculo/mostrar";
    }

    //Modifica un tipo de vehiculo
    @PostMapping("/modificar")
    public String modificarTipoVehiculo(@RequestParam("id") Long id, 
                                        @RequestParam("tipoVehiculoNombre") String tipoVehiculoNombre,
                                        @RequestParam("areaVehiculo") String areaVehiculo,
                                        @RequestParam("tarifa") String tarifa){
        TipoVehiculo tipoVehiculo = tipoVehiculoService.recuperarTipoVehiculo(id);
        //inserta los nuevos valores que no sean null
        if(!tipoVehiculoNombre.equals("")){
            tipoVehiculo.setTipoVehiculo(tipoVehiculoNombre);
        }
        if(!areaVehiculo.equals("")){
            tipoVehiculo.setAreaVehiculo(Float.parseFloat(areaVehiculo));
            //actualiza la capacidad de todos los pisos que tengan este tipo de vehiculo en un bucle for each
            for(Piso piso : pisoService.listarPisos()){
                if(piso.getTipoVehiculo().getId() == id){
                    piso.setTipoVehiculo(tipoVehiculo);
                    pisoService.agregarPiso(piso);
                }
            }
        }
        if(!tarifa.equals("")){
            tipoVehiculo.setTarifa(Float.parseFloat(tarifa));
        }
        tipoVehiculoService.agregarTipoVehiculo(tipoVehiculo);
        return "redirect:/tipoVehiculo/mostrar";
    }
    
    //Actualiza la pagina
    @GetMapping("/mostrar")
    public String actualizarPagina(Model model){
        model.addAttribute("tipoVehiculos", tipoVehiculoService.listarTipoVehiculo());
        return "tipo-vehiculo";
    }

}
