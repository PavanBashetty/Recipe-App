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

  getAllRecipesAPI():Observable<Recipe[]>{
    return this.http.get<Recipe[]>(`${environment.RECIPE_URL}/all`);
  }

  getYourRecipesAPI(customerId:number):Observable<Recipe[]>{
    return this.http.get<Recipe[]>(`${environment.RECIPE_URL}/customer/${customerId}`);
  }

  createRecipeAPI(newRecipe: Partial<Recipe>, customerId:number):Observable<StringResponse>{
    return this.http.post<StringResponse>(`${environment.RECIPE_URL}/customer/${customerId}`,newRecipe);
  }

  toggleLikeRecipeAPI(recipeId:number, customerId:number):Observable<Recipe>{
    return this.http.put<Recipe>(`${environment.RECIPE_URL}/${recipeId}/customer/${customerId}`,null);
  }
}
