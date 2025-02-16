import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from '../_model/interface/Recipe';
import { environment } from '../../environments/environment';
import { StringResponse } from '../_model/interface/StringResponse';

@Injectable({
  providedIn: 'root'
})
export class ApiService {


  constructor(private http:HttpClient) { }

  getAllRecipesRequest():Observable<Recipe[]>{
    return this.http.get<Recipe[]>(`${environment.RECIPE_URL}/all`);
  }

  getYourRecipesRequest(customerId:number):Observable<Recipe[]>{
    return this.http.get<Recipe[]>(`${environment.RECIPE_URL}/customer/${customerId}`);
  }

  createRecipeRequest(newRecipe: Partial<Recipe>, customerId:number):Observable<StringResponse>{
    return this.http.post<StringResponse>(`${environment.RECIPE_URL}/customer/${customerId}`,newRecipe);
  }

  toggleLikeRecipeRequest(recipeId:number, customerId:number):Observable<Recipe>{
    return this.http.put<Recipe>(`${environment.RECIPE_URL}/${recipeId}/customer/${customerId}`,null);
  }

  deleteRecipeRequest(recipeId:number):Observable<StringResponse>{
    return this.http.delete<StringResponse>(`${environment.RECIPE_URL}/${recipeId}`);
  }

  updateRecipeRequest(recipe:Partial<Recipe>, recipeId:number):Observable<Recipe>{
    return this.http.put<Recipe>(`${environment.RECIPE_URL}/${recipeId}`,recipe);
  }
}
