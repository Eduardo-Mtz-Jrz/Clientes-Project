import { Component } from '@angular/core';
import { AuthService } from '../services/auth';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inicio',
  imports: [FormsModule, CommonModule],
  templateUrl: './inicio.html',
  styleUrl: './inicio.css',
})
export class Inicio {
  loginData = { usuario: '', contrasenia: '' };
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

    onLogin(): void {
      this.authService.login({
        username: this.loginData.usuario,
        password: this.loginData.contrasenia
      }).subscribe({
        next: (res) => {
          console.log('Login exitoso', res);
          this.router.navigate(['/lista-clientes']);
        },
        error: (err) => {
          console.error('Error de autenticación', err);
          alert('Usuario o contraseña incorrectos');
        }
      });
    }
}
