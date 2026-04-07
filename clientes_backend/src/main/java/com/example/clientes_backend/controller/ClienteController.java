package com.example.clientes_backend.controller;

import com.example.clientes_backend.model.Cliente;
import com.example.clientes_backend.repository.ClienteRepository;
import com.example.clientes_backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/listado_clientes")
    public ResponseEntity<?> listado() {
        List<Cliente> clientes = clienteService.listarTodos();
        Map<String, Object> res = new HashMap<>();
        res.put("mensaje", "Éxito 200: Listado obtenido.");
        res.put("datos", clientes);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/add_cliente")
    public ResponseEntity<?> agregar(@RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.guardar(cliente);
        Map<String, Object> res = new HashMap<>();
        res.put("mensaje", "Éxito 201: Cliente registrado correctamente.");
        res.put("datos", nuevo);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/obtener_cliente/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    Map<String, Object> res = new HashMap<>();
                    res.put("mensaje", "Éxito 200: Cliente encontrado.");
                    res.put("datos", cliente);
                    return ResponseEntity.ok(res);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        // El 404 lo manejará mejor el GlobalExceptionHandler si lanzas una excepción
    }

    @PutMapping("/put_cliente/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente detalles) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(detalles.getNombre());
                    cliente.setApellido(detalles.getApellido());
                    cliente.setCorreo(detalles.getCorreo());
                    cliente.setTelefono(detalles.getTelefono());
                    Cliente actualizado = clienteRepository.save(cliente);

                    Map<String, Object> res = new HashMap<>();
                    res.put("mensaje", "Éxito 200: Información actualizada.");
                    res.put("datos", actualizado);
                    return ResponseEntity.ok(res);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/delete_cliente/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setStatus("INACTIVO"); // Cambiamos el valor
                    clienteRepository.save(cliente); // ¡ESTA LÍNEA ES VITAL!

                    Map<String, String> res = new HashMap<>();
                    res.put("mensaje", "Éxito 200: Cliente desactivado correctamente.");
                    return ResponseEntity.ok(res);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}