import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthResponse } from '../_model/interface/AuthResponse';
import { Customer } from '../_model/interface/customer';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authURL:string = "http://localhost:8080/api/auth";

  constructor(private http:HttpClient) { }

  register(newCustomer:Partial<Customer>):Observable<AuthResponse>{
    return this.http.post<AuthResponse>(`${this.authURL}/register`,newCustomer);
  }

  login(loginData:Partial<Customer>):Observable<any>{
    return this.http.post<any>(`${this.authURL}/login`,loginData);
  }

  storeToken(token:string){
    localStorage.setItem('jwtToken',token);
  }

  getToken():string | null{
    return localStorage.getItem('jwtToken');
  }

  isLoggedIn():boolean{
    return !!this.getToken();
  }
}
