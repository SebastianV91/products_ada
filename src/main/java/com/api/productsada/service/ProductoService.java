package com.api.productsada.service;

import com.api.productsada.model.Producto;
import com.api.productsada.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public Optional<Producto> getOne(int id){
        return productoRepository.findById(id);
    }

    public boolean existsById(int id){
        return productoRepository.existsById(id);
    }

}
