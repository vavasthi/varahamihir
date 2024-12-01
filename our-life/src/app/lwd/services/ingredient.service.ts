import {HttpClient, HttpContext, HttpHeaders} from '@angular/common/http';
import { Injectable, Signal } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { User } from '../../pojo/user';
import { IngredientWithNutrition } from '../pojo/ingredient-with-nutrition';
import { PageableIngredient } from '../pojo/pageable-ingredient';
import {RequestAuthOption} from "../../pojo/request-auth-option";

@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  baseUrl: string = "/api/v1";
  singleIngredientUrl: string = this.baseUrl + "/ingredient";
  allIngredientUrl: string = this.baseUrl + "/ingredient/1/50";
  user: Signal<User|undefined>;

  constructor(private authService: AuthService,
    private httpClient:HttpClient) {
      this.user = this.authService.getCurrentUser()
    }
  getHttpOptions():HttpHeaders {

    return new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
    }

  createIngredient(ingredientWithNutrition: IngredientWithNutrition) {

    return this
      .httpClient
      .post<IngredientWithNutrition>(this.singleIngredientUrl, ingredientWithNutrition,
        {
          context: new HttpContext().set(RequestAuthOption.AUTH_TOKEN, true),
          headers:this.getHttpOptions()
        })
  }
  getAllIngredient() {

    return this
      .httpClient
      .get<PageableIngredient>(this.allIngredientUrl,
        {
          context: new HttpContext().set(RequestAuthOption.AUTH_TOKEN, true),
          headers:this.getHttpOptions()
        })
  }
  getIngredient(id:string) {

    return this
      .httpClient
      .get<IngredientWithNutrition>(this.singleIngredientUrl + "/" + id, {headers:this.getHttpOptions()})
  }
}
