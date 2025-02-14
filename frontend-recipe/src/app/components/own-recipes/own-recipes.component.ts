import { Component } from '@angular/core';
import { RecipeCardComponent } from '../recipe-card/recipe-card.component';
import { Recipe } from '../../_model/interface/Recipe';

@Component({
  selector: 'app-own-recipes',
  imports: [RecipeCardComponent],
  templateUrl: './own-recipes.component.html',
  styleUrl: './own-recipes.component.scss'
})
export class OwnRecipesComponent {

  ownRecipeList:Recipe[] = [];

}
