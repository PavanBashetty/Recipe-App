import { Component, Optional, SkipSelf } from '@angular/core';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { Recipe } from '../../_model/interface/Recipe';
import { SharedService } from '../../services/shared.service';
import { ApiService } from '../../services/api.service';
import { MatButtonModule } from '@angular/material/button';
import {MatRadioModule} from '@angular/material/radio';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-create-recipe',
  imports: [MatFormFieldModule,MatInputModule,MatSelectModule, FormsModule, MatButtonModule, MatRadioModule],
  templateUrl: './create-recipe.component.html',
  styleUrl: './create-recipe.component.scss'
})
export class CreateRecipeComponent {

  recipeItem:Partial<Recipe> = {
    title:'',
    description:'',
    image:'',
    isVeg:false
  }
  customerId!:number;


  constructor(private sharedService:SharedService, private apiService:ApiService, private dialogRef:MatDialog){}

  ngOnInit(){}

  submitNewRecipe(){
    this.customerId = Number(this.sharedService.getCustomerId());
    this.apiService.createRecipeAPI(this.recipeItem, this.customerId).subscribe({
      next:(response)=>{
        alert(response['message']);
        this.cancel();
        this.sharedService.triggerDashboardRefresh();
      },
      error:(error)=>{console.error(error)}
    })
  }

  cancel(){
    this.dialogRef.closeAll();
  }
}
