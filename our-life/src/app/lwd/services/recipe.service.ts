import { Injectable, Signal } from '@angular/core';
import { Recipe } from '../pojo/recipe';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { User } from '../../pojo/user';
import { PageableRecipe } from '../pojo/pageable-recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  baseUrl: string = "/api/v1";
  recipeUrl: string = this.baseUrl + "/recipe/1/50";

  user: Signal<User|undefined>;
  
  constructor(private authService: AuthService,
    private httpClient:HttpClient) { 
      this.user = this.authService.getCurrentUser()
  }
  
  getHttpOptions():HttpHeaders {

    return new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
      .set('Authorization', 'Bearer ' + this.user()?.authToken)
    }
  
  createRecipe(recipe:Recipe) {

    return this
      .httpClient
      .post<Recipe>(this.recipeUrl, recipe, {headers:this.getHttpOptions()})
  }
  getAllRecipe() {

    return this
      .httpClient
      .get<PageableRecipe>(this.recipeUrl, {headers:this.getHttpOptions()})
  }
}
