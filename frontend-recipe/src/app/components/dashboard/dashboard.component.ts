import { Component, TemplateRef, ViewChild } from '@angular/core';
import { RecipeCardComponent } from '../recipe-card/recipe-card.component';
import { ApiService } from '../../services/api.service';
import { Recipe } from '../../_model/interface/Recipe';
import {MatIconModule} from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { CreateRecipeComponent } from '../create-recipe/create-recipe.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { SharedService } from '../../services/shared.service';

@Component({
  selector: 'app-dashboard',
  imports: [RecipeCardComponent, MatIconModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  recipeList:Recipe[] = [];

  constructor(private apiService:ApiService, private dialogRef:MatDialog, private sharedService:SharedService){}

  ngOnInit(){

    this.sharedService.refreshDashboard$.subscribe({
      next:()=>{
        this.getAllRecipes();
      },
      error:(error)=>{console.error(error)}
    })

    this.getAllRecipes();
  }

  getAllRecipes(){
    this.apiService.getAllRecipesAPI().subscribe({
      next:(data:Recipe[])=>{
        this.recipeList = data;
      },
      error:(error)=>{console.error(error)}
    })
  }

  openCreateRecipeDialog(){
    this.dialogRef.open(CreateRecipeComponent)
  }
}
