import { Injectable, Signal, WritableSignal, signal } from '@angular/core';
import { User } from '../../pojo/user';
import { AuthService } from '../../services/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Unit } from '../pojo/unit';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class UnitService {

  baseUrl: string = "/api/v1";
  unitUrl: string = this.baseUrl + "/unit";

  user: Signal<User|undefined>;
  units:WritableSignal<Unit[]|undefined> = signal(undefined);
  
  constructor(private authService: AuthService,
    private snackBar:MatSnackBar,
    private httpClient:HttpClient) { 
      this.user = this.authService.getCurrentUser()
      this.loadAllUnits(false)
  }
  
  getHttpOptions():HttpHeaders {

    return new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
      .set('Authorization', 'Bearer ' + this.user()?.authToken)
  }
  
  getUnits():Signal<Unit[]|undefined> {
    return this.units
  }
  loadAllUnits(retrying:boolean) {

    return this
      .httpClient
      .get<Unit[]>(this.unitUrl, {headers:this.getHttpOptions()}).subscribe({
        next: (response) => {
          this.units.set(response);
        },
        error: (err) => {
          if (err.status == 403 && !retrying) {
            this.authService.refresh();
            this.loadAllUnits(true);
          }
          else {
  
            console.log(err)
            this.snackBar.open("Units Load Failed", "Ok", { duration: 5000 })
            }
        }
      })
  }
}
