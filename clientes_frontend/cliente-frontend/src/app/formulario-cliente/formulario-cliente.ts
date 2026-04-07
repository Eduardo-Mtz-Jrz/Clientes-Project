import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ClienteService } from '../cliente';

@Component({
  selector: 'app-formulario-cliente',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './formulario-cliente.html',
  styleUrl: './formulario-cliente.css'
})
export class FormularioCliente implements OnInit {
  clienteForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.clienteForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      apellido: ['', [Validators.required, Validators.minLength(2)]],
      curp: ['', [Validators.required, Validators.pattern(/^[A-Z]{4}\d{6}[HM][A-Z]{5}[A-Z\d]\d$/)]],
      correo: ['', [Validators.required, Validators.email]],
      telefono: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      puesto: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  guardarCliente(): void {
    if (this.clienteForm.valid) {
      const datosCliente = this.clienteForm.value;

      this.clienteService.registrarCliente(datosCliente).subscribe({
        next: (response) => {
          alert('¡Cliente registrado con éxito!');
          this.clienteForm.reset(); // Limpia el formulario tras el éxito
        },
        error: (err) => {
          console.error('Error al registrar:', err);
          alert('Error al conectar con el servidor. Revisa el token.');
        }
      });
    }
  }
}
