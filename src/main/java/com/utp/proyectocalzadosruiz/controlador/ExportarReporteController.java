/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.proyectocalzadosruiz.controlador;

import java.io.ByteArrayOutputStream;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author rodri
 */
public abstract class ExportarReporteController {

    protected ResponseEntity<byte[]> generarArchivoCSV(ByteArrayOutputStream out, String nombreArchivo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(
            ContentDisposition.attachment().filename(nombreArchivo).build()
        );
        return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
    }

}
