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
@RequestMapping("/pisos")
public class PisosController {
    
    @Autowired
    private PisoService pisoService;

    @Autowired
    private TipoVehiculoService tipoVehiculoService;


    @GetMapping("/mostrar")
    public String pisos(Model model) {
        
        model.addAttribute("pisos", pisoService.listarPisos());
        model.addAttribute("tipoVehiculos", tipoVehiculoService.listarTipoVehiculo());
        return "pisos";
    
    }

    //agrega un piso
    @PostMapping("/agregar")
    public String agregarPiso(@RequestParam("numPiso") int piso, @RequestParam("tipoVehiculo") String tipoVehiculoNombre, @RequestParam("area") int area){

        TipoVehiculo tipoVehiculo = tipoVehiculoService.recuperarTipoVehiculo(tipoVehiculoNombre);
        pisoService.agregarPiso(new Piso(piso, area, tipoVehiculo));
        return "redirect:/pisos/mostrar";
    
    }

    //modifica un piso
    @PostMapping("/modificar")
    public String modificarPiso(@RequestParam("numPiso") String numPiso, @RequestParam("tipoVehiculo") String tipoVehiculoNombre, @RequestParam("idPiso") Long id, @RequestParam("area") String area){
            
            Piso piso = pisoService.recuperarPiso(id);
            //inserta los nuevos valores que no sean null
            if(!numPiso.equals("")){
                piso.setPiso(Integer.parseInt(numPiso));
            }
            if(!area.equals("")){
                piso.setArea(Integer.parseInt(area));
            }
            if(!tipoVehiculoNombre.equals("")){
                TipoVehiculo tipoVehiculo = tipoVehiculoService.recuperarTipoVehiculo(tipoVehiculoNombre);
                piso.setTipoVehiculo(tipoVehiculo);
            }
            pisoService.agregarPiso(piso);
            return "redirect:/pisos/mostrar";

    }

    //borra un piso con el id
    @PostMapping("/borrar")
    public String borrarPiso(@RequestParam("idBorrar") Long id){

        pisoService.borrarPiso(id);
        return "redirect:/pisos/mostrar";

    }

        
}
