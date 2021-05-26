package com.labcr.demo.controller;

import com.labcr.demo.Entity.Persona;
import com.labcr.demo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private SecurityService securityService;
    private String nombre;

    @Autowired
    public UserController(SecurityService securityService) {
        this.securityService = securityService;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @RequestMapping("/home")
    public String hola(Model modelo) {
        Persona p = securityService.getPersonaActiva();
        modelo.addAttribute("nombre", "Nombre: " + p.getNombre());
        modelo.addAttribute("correo", "Correo: " + p.getEmail());
        modelo.addAttribute("uuid", "id Keycloak: " + p.getUuid() + "");
        return "home";
    }
}
