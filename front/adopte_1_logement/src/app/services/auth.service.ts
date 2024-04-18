import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiUrl } from 'environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  AUTH_API : string = apiUrl + 'auth/';
  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    return this.http.post(
      this.AUTH_API + 'login',
      {
        email: email,
        password: password
      }
    );
  }

  register(firstName: string, lastName: string, email: string, password: string): Observable<any> {
    return this.http.post(
      this.AUTH_API + 'register',
      {
        firstName,
        lastName,
        email,
        password,
      }
    );
  }
}
