import { Component, Input } from '@angular/core';
import { ApiService } from '../../services/api.service';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { Recipe } from '../../_model/interface/Recipe';
import {MatIconModule} from '@angular/material/icon';
import { SharedService } from '../../services/shared.service';
import { MatDialog } from '@angular/material/dialog';
import { CreateRecipeComponent } from '../create-recipe/create-recipe.component';

@Component({
  selector: 'app-recipe-card',
  imports: [MatButtonModule,MatCardModule, MatIconModule],
  templateUrl: './recipe-card.component.html',
  styleUrl: './recipe-card.component.scss'
})
export class RecipeCardComponent {

  @Input() recipes!:Recipe[];
  constructor(private apiService:ApiService, private sharedService:SharedService, private dialogRef:MatDialog){}
  customerId!:number;
  @Input() displayEditDeleteBtn!:boolean;

  ngOnInit(){}

  toggleLikeRecipe(recipeId:number){
    this.customerId = Number(this.sharedService.getCustomerId());
    this.apiService.toggleLikeRecipeRequest(recipeId,this.customerId).subscribe({
      next:(data:Recipe)=>{
        alert('success');
        if(this.displayEditDeleteBtn){
          this.sharedService.triggerOwnRecipeRefresh();
        }else{
          this.sharedService.triggerDashboardRefresh();
        }
      },
      error:(error)=>{console.error(error)}
    })
  }

  deleteRecipe(recipeId:number){
    this.apiService.deleteRecipeRequest(recipeId).subscribe({
      next:(response)=>{
        alert(response['message']);
        this.sharedService.triggerOwnRecipeRefresh();
      },
      error:(error)=>{console.error(error)}
    })
  }

  editRecipe(selectedRecipe:Recipe, selectedRecipeId:number){
    this.dialogRef.open(CreateRecipeComponent,{
      data:{
        recipe:selectedRecipe,
        recipeId:selectedRecipeId
      }
    })
  }
}
