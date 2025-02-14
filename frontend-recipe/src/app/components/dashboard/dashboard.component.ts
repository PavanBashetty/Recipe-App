import { Component } from '@angular/core';
import { RecipeCardComponent } from '../recipe-card/recipe-card.component';
import { ApiService } from '../../services/api.service';
import { Recipe } from '../../_model/interface/Recipe';
import {MatIconModule} from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-dashboard',
  imports: [RecipeCardComponent, MatIconModule, MatButtonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  recipeList:Recipe[] = [];
  constructor(private apiService:ApiService){}

  ngOnInit(){
    this.getAllRecipes();
  }

  getAllRecipes(){
    this.apiService.getAllRecipesAPI().subscribe({
      next:(data:Recipe[])=>{
        this.recipeList = data;
        console.log(this.recipeList);
      },
      error:(error)=>{console.error(error)}
    })
  }
}
