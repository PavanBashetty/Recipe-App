import { Component, Input } from '@angular/core';
import { ApiService } from '../../services/api.service';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { Recipe } from '../../_model/interface/Recipe';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-recipe-card',
  imports: [MatButtonModule,MatCardModule, MatIconModule],
  templateUrl: './recipe-card.component.html',
  styleUrl: './recipe-card.component.scss'
})
export class RecipeCardComponent {

  @Input() recipes!:Recipe[];
  constructor(private apiService:ApiService){}

  ngOnInit(){}
}
