import { Injectable, Signal, WritableSignal, signal } from '@angular/core';
import { User } from '../pojo/user';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Unit } from '../pojo/unit';

@Injectable({
  providedIn: 'root'
})
export class UnitService {

  baseUrl: string = "/api/v1";
  unitUrl: string = this.baseUrl + "/unit";

  user: Signal<User|undefined>;
  units:WritableSignal<Unit[]|undefined> = signal(undefined);
  
  constructor(private authService: AuthService,
    private httpClient:HttpClient) { 
      this.user = this.authService.getCurrentUser()
      this.loadAllUnits();
  }
  
  getHttpOptions():HttpHeaders {

    return new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
      .set('Authorization', 'Bearer ' + this.user()?.authToken)
  }
  
  getUnits():Signal<Unit[]|undefined> {
    return this.units
  }
  loadAllUnits() {

    return this
      .httpClient
      .get<Unit[]>(this.unitUrl, {headers:this.getHttpOptions()}).subscribe(response => {
        this.units.set(response);
      })
  }
}
