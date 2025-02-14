import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Customer } from '../../_model/interface/customer';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerForm:FormGroup = new FormGroup({});
  newCustomer!:Partial<Customer>;

  constructor(private authService:AuthService, private formBuilder:FormBuilder, private router:Router){}

  ngOnInit(){
    this.registerForm = this.formBuilder.group({
      fullName:['',Validators.required],
      password:['',Validators.required],
      email:['',Validators.required]
    })
  }

  register(){
    this.newCustomer = this.registerForm.value;
    this.authService.register(this.newCustomer).subscribe({
      next:(response)=>{
        alert(response['message']);
        this.router.navigate(['/login']);
      },
      error:(error)=>{
        alert("Register failed!");
        this.registerForm.reset();
        console.error(error);
      }
    })
  }
}
