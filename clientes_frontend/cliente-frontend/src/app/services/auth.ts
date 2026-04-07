import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:9001/auth/login';

  constructor(private http: HttpClient) { }

  login(credentials: { username: string, password: string }): Observable<any> {
      return this.http.post<any>(this.apiUrl, credentials).pipe(
        tap(response => {
          // Si el back devuelve { token: "..." }, lo guardamos
          if (response && response.token) {
            localStorage.setItem('token', response.token);
          }
        })
    );
  }

  isLoggedIn(): boolean {
      return !!localStorage.getItem('token');
    }

    logout() {
      localStorage.removeItem('token');
    }
  }
