package com.api.productsada.repository;

import com.api.productsada.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    public void testGuardarProducto() {
        Producto producto = new Producto("Teclado", "Teclado mecánico", 120.0);
        Producto guardado = productoRepository.save(producto);

        assertNotNull(guardado.getId());
        assertEquals("Teclado", guardado.getNombre());
    }

    @Test
    public void testBuscarPorId() {
        Producto producto = new Producto("Mouse", "Mouse óptico", 80.0);
        producto = productoRepository.save(producto);

        Optional<Producto> encontrado = productoRepository.findById(producto.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Mouse", encontrado.get().getNombre());
    }

}
