package com.example.clientes_backend;

import com.example.clientes_backend.controller.ClienteController;
import com.example.clientes_backend.model.Cliente;
import com.example.clientes_backend.service.ClienteService;
import com.example.clientes_backend.repository.ClienteRepository;
import com.example.clientes_backend.repository.UsuarioRepository;
import com.example.clientes_backend.security.JwtService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @MockitoBean
    private ClienteRepository clienteRepository;

    @MockitoBean private UsuarioRepository usuarioRepository;
    @MockitoBean private JwtService jwtService;
    @MockitoBean private PasswordEncoder passwordEncoder;

    @Test
    public void testListadoClientesExitoso() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Eduardo");
        cliente.setApellido("Martinez");

        when(clienteService.listarTodos()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/clientes/listado_clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Éxito 200: Listado obtenido.")))
                .andExpect(jsonPath("$.datos", hasSize(1)))
                .andExpect(jsonPath("$.datos[0].nombre", is("Eduardo")));
    }
}