import { Injectable, Signal } from '@angular/core';
import { Recipe } from '../pojo/recipe';
import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { User } from '../../pojo/user';
import { PageableRecipe } from '../pojo/pageable-recipe';
import { RequestAuthOption } from '../../pojo/request-auth-option';

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
    }

  createRecipe(recipe:Recipe) {

    return this
      .httpClient
      .post<Recipe>(this.recipeUrl, recipe, {headers:this.getHttpOptions()})
  }
  getAllRecipe() {

    return this
      .httpClient
      .get<PageableRecipe>(this.recipeUrl,
        {
          context: new HttpContext().set(RequestAuthOption.AUTH_TOKEN, true),
          headers:this.getHttpOptions()
        })
  }
}
