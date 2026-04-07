package com.example.clientes_backend.controller;

import com.example.clientes_backend.model.Usuario;
import com.example.clientes_backend.repository.UsuarioRepository;
import com.example.clientes_backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        // 1. Verificar que los datos lleguen
        if (loginRequest == null || loginRequest.getUsername() == null) {
            return ResponseEntity.badRequest().body("Datos de login incompletos");
        }

        return usuarioRepository.findByUsername(loginRequest.getUsername())
                .map(user -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                        String token = jwtService.generateToken(user.getUsername());
                        // Importante: No olvides actualizar el token en el objeto usuario si así lo requiere tu lógica
                        user.setToken(token);
                        usuarioRepository.save(user);
                        return ResponseEntity.ok(Map.of("token", token));
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado"));
    }
}