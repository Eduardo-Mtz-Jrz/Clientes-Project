import { Routes } from '@angular/router';
import { Inicio } from './inicio/inicio';
import { ListaClientes } from './lista-clientes/lista-clientes';
import { PaginaCliente } from './pagina-cliente/pagina-cliente';
import { FormularioCliente } from './formulario-cliente/formulario-cliente';

export const routes: Routes = [
  { path: 'inicio', component: Inicio },
  { path: 'pagina-clientes', component: PaginaCliente },
  { path: 'lista-clientes', component: ListaClientes },
  { path: 'nuevo-registro', component: FormularioCliente },
  { path: 'registro-cliente', component: FormularioCliente },
  { path: '', redirectTo: '/inicio', pathMatch: 'full' }
];
