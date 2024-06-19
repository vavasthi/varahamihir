import { Injectable, Signal } from '@angular/core';
import { User } from '../pojo/user';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PageableEquation } from '../pojo/pageable-equation';

@Injectable({
  providedIn: 'root'
})
export class EquationService {

  baseUrl: string = "/api/v1";
  singleEquationUrl: string = this.baseUrl + "/equation";
  allEquationUrl: string = this.baseUrl + "/equation/0/50";
  user: Signal<User|undefined>;

  constructor(private authService: AuthService,
    private httpClient:HttpClient) {
      this.user = this.authService.getCurrentUser()
    }
  getHttpOptions():HttpHeaders {

    return new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
    }

//  createIngredient(IngredientWithNutrition: IngredientWithNutrition) {
//
//    return this
//      .httpClient
 //     .post<IngredientWithNutrition>(this.singleIngredientUrl, IngredientWithNutrition, {headers:this.getHttpOptions()})
//  }
  getAllEquations() {

    return this
      .httpClient
      .get<PageableEquation>(this.allEquationUrl, {headers:this.getHttpOptions()})
  }
}
