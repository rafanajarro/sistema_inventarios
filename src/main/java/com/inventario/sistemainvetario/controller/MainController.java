package com.inventario.sistemainvetario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inventario.sistemainvetario.model.Empresa;
import com.inventario.sistemainvetario.service.EmpresaService;

@Controller
public class MainController {

    @RequestMapping(method = RequestMethod.GET, path = { "/", "", "/inicio", "/index" })
    public String obtenerPaginaPrincipal(Model model) {
        return "index.html";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String requestMethodName(Model model) {
        return "login.html";
    }
}
