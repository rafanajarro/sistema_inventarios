package com.inventario.sistemainvetario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inventario.sistemainvetario.model.RolUsuario;
import com.inventario.sistemainvetario.service.RolUsuarioService;
import com.inventario.sistemainvetario.service.UsuarioService;

public class UsuariosController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolUsuarioService rolUsuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;
}
