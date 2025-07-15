package com.api.productsada.controller;

import com.api.productsada.dto.ProductoDTO;
import com.api.productsada.model.Producto;
import com.api.productsada.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCrearProducto_Valido() throws Exception {
        ProductoDTO dto = new ProductoDTO("Computador", "Computador de escritorio", 1500.0);
        Producto producto = new Producto(dto.getNombre(), dto.getDescripcion(), dto.getPrecio());

        when(productoService.save(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/producto/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje").value("Producto agregado"));
    }

    @Test
    public void testCrearProducto_NombreInvalido() throws Exception {
        ProductoDTO dto = new ProductoDTO("", "Descripción", 500.0);

        mockMvc.perform(post("/producto/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("el nombre es obligatorio"));
    }

    @Test
    public void testCrearProducto_PrecioNegativo() throws Exception {
        ProductoDTO dto = new ProductoDTO("Mouse", "Ratón óptico", -20.0);

        mockMvc.perform(post("/producto/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("el precio debe ser mayor que 0"));
    }

    @Test
    public void testGetById_Existe() throws Exception {
        Producto producto = new Producto("Teclado", "Teclado mecánico", 300.0);
        producto.setId(1);

        when(productoService.existsById(1)).thenReturn(true);
        when(productoService.getOne(1)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/producto/detail/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Teclado"))
                .andExpect(jsonPath("$.precio").value(300.0));
    }

    @Test
    public void testGetById_NoExiste() throws Exception {
        when(productoService.existsById(99)).thenReturn(false);

        mockMvc.perform(get("/producto/detail/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Este producto solicitado no existe"));
    }

}
