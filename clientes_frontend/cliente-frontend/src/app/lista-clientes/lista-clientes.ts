import { Component, OnInit } from '@angular/core'; // Para el error NG2007 y OnInit
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms'; // Para errores de FormGroup, FormBuilder y Validators
import { ClienteService, Cliente } from '../cliente'; // Para el error de ClienteService

@Component({
  selector: 'app-lista-clientes',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './lista-clientes.html', // Asegúrate de que el nombre coincida con tu .html
  styleUrls: ['./lista-clientes.css']    // Si no tienes CSS, puedes borrar esta línea
})
export class ListaClientes implements OnInit {
  clientes: Cliente[] = [];
  clienteForm!: FormGroup;
  editando: boolean = false;
  idSeleccionado: number | null = null;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService
  ) {
    this.initForm();
  }

  ngOnInit(): void {
    this.cargarClientes(); // Ahora este método sí existirá abajo
  }

  initForm(): void {
    this.clienteForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      curp: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      puesto: ['', Validators.required]
    });
  }

  // Este es el método que te faltaba y causaba el error TS2339
  cargarClientes(): void {
    this.clienteService.listarClientes().subscribe({
      next: (res: Cliente[]) => { // Agregamos : Cliente[]
        this.clientes = res;
      },
      error: (err: any) => console.error('Error al cargar clientes', err) // Agregamos : any
    });
  }

  enviarFormulario(): void {
    if (this.clienteForm.invalid) return;

    if (this.editando && this.idSeleccionado) {
      this.clienteService.actualizarCliente(this.idSeleccionado, this.clienteForm.value).subscribe({
        next: (res: Cliente) => this.finalizarAccion('Cliente actualizado con éxito'),
        error: (err: any) => alert('Error al actualizar')
      });
    } else {
      this.clienteService.registrarCliente(this.clienteForm.value).subscribe({
        next: (res: Cliente) => this.finalizarAccion('Cliente registrado con éxito'),
        error: (err: any) => alert('Error al registrar')
      });
    }
  }

  eliminar(id?: number): void {
    if (id && confirm('¿Estás seguro de eliminar este cliente?')) {
      this.clienteService.eliminarCliente(id).subscribe({
        next: () => this.cargarClientes(), // Aquí no hay res, así que no falla
        error: (err: any) => alert('Error al eliminar') // Agregamos : any
      });
    }
  }

  prepararEdicion(cliente: Cliente): void {
    this.editando = true;
    this.idSeleccionado = cliente.id ?? null;
    this.clienteForm.patchValue(cliente);
  }

  cancelarEdicion(): void {
    this.editando = false;
    this.idSeleccionado = null;
    this.clienteForm.reset();
  }

  private finalizarAccion(msg: string): void {
    alert(msg);
    this.cancelarEdicion();
    this.cargarClientes();
  }
}
