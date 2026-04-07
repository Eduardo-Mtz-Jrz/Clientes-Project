package com.example.clientes_backend.controller;

import com.example.clientes_backend.model.Usuario;
import com.example.clientes_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listado")
    public ResponseEntity<?> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        Map<String, Object> res = new HashMap<>();
        res.put("mensaje", "Éxito 200: Listado de usuarios obtenido.");
        res.put("datos", usuarios);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    Map<String, Object> res = new HashMap<>();
                    res.put("mensaje", "Éxito 200: Usuario encontrado.");
                    res.put("datos", usuario);
                    return ResponseEntity.ok(res);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario detalles) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setUsername(detalles.getUsername());
                    // Nota: Si actualizas password, recuerda usar el encoder
                    if(detalles.getPassword() != null) usuario.setPassword(detalles.getPassword());

                    Usuario actualizado = usuarioRepository.save(usuario);
                    Map<String, Object> res = new HashMap<>();
                    res.put("mensaje", "Éxito 200: Usuario actualizado correctamente.");
                    res.put("datos", actualizado);
                    return ResponseEntity.ok(res);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setStatus("INACTIVO");
                    usuarioRepository.save(usuario);

                    Map<String, String> res = new HashMap<>();
                    res.put("mensaje", "Éxito 200: El usuario ha sido desactivado del sistema.");
                    return ResponseEntity.ok(res);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}