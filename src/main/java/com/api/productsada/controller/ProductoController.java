package com.api.productsada.controller;

import com.api.productsada.dto.Mensaje;
import com.api.productsada.dto.ProductoDTO;
import com.api.productsada.model.Producto;
import com.api.productsada.service.ProductoService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDTO productoDTO){

        if(StringUtils.isBlank(productoDTO.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDTO.getPrecio()==null || productoDTO.getPrecio()<0 )
            return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        Producto producto = new Producto(productoDTO.getNombre(), productoDTO.getDescripcion(), productoDTO.getPrecio());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto agregado"), HttpStatus.CREATED);

    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("Este producto solicitado no existe"), HttpStatus.NOT_FOUND);

        Producto producto = productoService.getOne(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

}
