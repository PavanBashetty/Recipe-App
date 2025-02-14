import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Customer } from '../../_model/interface/customer';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm:FormGroup = new FormGroup({});
  loginData!:Partial<Customer>

  constructor(private authService:AuthService, private formBuilder:FormBuilder, private router:Router){}

  ngOnInit(){
    this.loginForm = this.formBuilder.group({
      email:['',Validators.required],
      password:['',Validators.required]
    })
  }

  login(){
    this.loginData = this.loginForm.value;
    this.authService.login(this.loginData).subscribe({
      next:(response)=>{
        const token = response['token'];
        const decodedToken = this.authService.decodeToken(token);
        this.authService.storeCustomerId(decodedToken.customerId);
        this.authService.storeToken(token);
        alert("Login successfull");
        this.router.navigate(['/dashboard'])
      },
      error:(error)=>{
        this.loginForm.reset();
        console.error(error.error.error);
      }
    })
  }
}