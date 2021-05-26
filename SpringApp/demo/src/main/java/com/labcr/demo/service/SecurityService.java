/******************************************************************************
 * Consejo Nacional de Rectores (Conare) Derechos de Autor (c) 2020           *
 * ADI Área de Desarrollo Institucional                                       *
 * ATIC Área de Tecnologías de Información y Comunicación                     *
 ******************************************************************************/
package com.labcr.demo.service;

import com.labcr.demo.Entity.Persona;

import java.util.List;

public interface SecurityService {

    Boolean crearUsuario(Persona persona, String password, List<String> roles, boolean activo);

    Persona getPersonaActiva();

    String modificarUsuario(Persona persona);

    boolean validarContrasenna(Persona persona, String contrasena);
}
