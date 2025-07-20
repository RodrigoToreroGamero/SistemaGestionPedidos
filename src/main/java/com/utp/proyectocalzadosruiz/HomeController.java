/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author rodri
 */

@Controller
public class HomeController {
    @GetMapping("/")
    public String mostrarHome(Model model) {
        model.addAttribute("vendedorId", 1L); // En tu prototipo, lo fijas aquí
        return "home"; // Renderiza home.html
    }
}
