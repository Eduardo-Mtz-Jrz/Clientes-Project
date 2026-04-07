import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaCliente } from './pagina-cliente';

describe('PaginaCliente', () => {
  let component: PaginaCliente;
  let fixture: ComponentFixture<PaginaCliente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaCliente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaCliente);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
