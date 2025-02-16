import { Component } from '@angular/core';
import { RecipeCardComponent } from '../recipe-card/recipe-card.component';
import { Recipe } from '../../_model/interface/Recipe';
import { ApiService } from '../../services/api.service';
import { SharedService } from '../../services/shared.service';

@Component({
  selector: 'app-own-recipes',
  imports: [RecipeCardComponent],
  templateUrl: './own-recipes.component.html',
  styleUrl: './own-recipes.component.scss'
})
export class OwnRecipesComponent {

  ownRecipeList:Recipe[] = [];
  customerId!:number;
  constructor(private apiService:ApiService, private sharedService:SharedService){}

  ngOnInit(){
    this.sharedService.refreshOwnRecipe$.subscribe({
      next:()=>{this.getYourRecipes()},
      error:(error)=>{console.error(error)}
    })

    this.getYourRecipes();
  }

  getYourRecipes(){
    this.customerId = Number(this.sharedService.getCustomerId());
    this.apiService.getYourRecipesRequest(this.customerId).subscribe({
      next:(data:Recipe[])=>{
        this.ownRecipeList = data;
      },
      error:(error)=>{console.error(error)}
    })
  }

}
