package com.api.productsada.service;

import com.api.productsada.model.Producto;
import com.api.productsada.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public void save(Producto producto){
        productoRepository.save(producto);
    }

}
