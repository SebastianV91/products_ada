package com.api.productsada.service;


import com.api.productsada.model.Producto;
import com.api.productsada.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarProducto() {
        Producto producto = new Producto("Celular", "Smartphone gama media", 1000.0);
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto resultado = productoService.save(producto);

        assertNotNull(resultado);
        assertEquals("Celular", resultado.getNombre());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testBuscarProductoPorId (){
        Producto producto = new Producto("Tablet", "Tablet 10 pulgadas", 600.0);
        producto.setId(1);

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.getOne(1);

        assertTrue(resultado.isPresent());
        assertEquals("Tablet", resultado.get().getNombre());
        verify(productoRepository, times(1)).findById(1);
    }

    @Test
    void testExisteProductoPorId() {
        when(productoRepository.existsById(5)).thenReturn(true);

        boolean existe = productoService.existsById(5);

        assertTrue(existe);
        verify(productoRepository, times(1)).existsById(5);
    }

}
