package com.api.productsada.controller;

import com.api.productsada.dto.Mensaje;
import com.api.productsada.dto.ProductoDTO;
import com.api.productsada.model.Producto;
import com.api.productsada.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDTO productoDTO){

        Producto producto = new Producto(productoDTO.getNombre(), productoDTO.getDescripcion(), productoDTO.getPrecio());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto agregado"), HttpStatus.CREATED);

    }

}
