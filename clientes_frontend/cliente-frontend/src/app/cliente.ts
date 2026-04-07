import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Cliente {
  id?: number;
  nombre: string;
  apellido: string;
  curp: string;
  correo: string;
  telefono: string;
  puesto: string;
  fechaRegistro?: Date;
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private apiUrl = 'http://localhost:9001/clientes';

  constructor(private http: HttpClient) { }

    listarClientes(): Observable<Cliente[]> {
      return this.http.get<Cliente[]>(`${this.apiUrl}/listado_clientes`);
    }

    // Registrar un cliente nuevo
    registrarCliente(cliente: Cliente): Observable<Cliente> {
      return this.http.post<Cliente>(`${this.apiUrl}/add_cliente`, cliente);
    }

    // ACTUALIZAR: Este método es el que usará el botón de "Editar"
    // Generalmente en Java se usa /update_cliente/{id} o simplemente /update_cliente
    actualizarCliente(id: number, cliente: Cliente): Observable<Cliente> {
      return this.http.put<Cliente>(`${this.apiUrl}/update_cliente/${id}`, cliente);
    }

    // Eliminar un cliente
    eliminarCliente(id: number): Observable<void> {
      return this.http.delete<void>(`${this.apiUrl}/delete_cliente/${id}`);
    }
  }
