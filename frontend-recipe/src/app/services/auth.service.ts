import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthResponse } from '../_model/interface/AuthResponse';
import { Customer } from '../_model/interface/customer';
import { environment } from '../../environments/environment';
import {jwtDecode} from 'jwt-decode';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }

  register(newCustomer:Partial<Customer>):Observable<AuthResponse>{
    return this.http.post<AuthResponse>(`${environment.AUTH_URL}/register`,newCustomer);
  }

  login(loginData:Partial<Customer>):Observable<any>{
    return this.http.post<any>(`${environment.AUTH_URL}/login`,loginData);
  }

  storeToken(token:string){
    localStorage.setItem('jwtToken',token);
  }

  storeCustomerId(customerId:string){
    localStorage.setItem('customerId',customerId);
  }

  getToken():string | null{
    return localStorage.getItem('jwtToken');
  }

  isLoggedIn():boolean{
    return !!this.getToken();
  }

  decodeToken(token:string):any{
    return jwtDecode(token);
  }
}
