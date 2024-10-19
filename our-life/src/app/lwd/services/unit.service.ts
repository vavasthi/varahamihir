import { Injectable, Signal, WritableSignal, signal } from '@angular/core';
import { User } from '../../pojo/user';
import { AuthService } from '../../services/auth.service';
import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Unit } from '../pojo/unit';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RequestAuthOption } from '../../pojo/request-auth-option';

@Injectable({
  providedIn: 'root'
})
export class UnitService {

  baseUrl: string = "/api/v1";
  unitUrl: string = this.baseUrl + "/unit";

  user: Signal<User|undefined>;
  units:WritableSignal<Unit[]|undefined> = signal(undefined);
  unitTypes:WritableSignal<String[]|undefined> = signal(undefined);

  constructor(private authService: AuthService,
    private snackBar:MatSnackBar,
    private httpClient:HttpClient) {
      this.user = this.authService.getCurrentUser()
      this.loadAllUnits().subscribe({
        next: (response) => {
          console.log(response)
          this.units.set(response)
        },
        error: (err) => {
          console.log(err)
          this.snackBar.open("Unit load failed.", "Ok", {duration : 5000})
        }
      })
  }

  getHttpOptions():HttpHeaders {

    return new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
  }

  getUnits():Signal<Unit[]|undefined> {
    return this.units
  }
  getUnitTypes():Signal<String[]|undefined> {
    return this.unitTypes
  }
  loadAllUnits() {

    return this
      .httpClient
      .get<Unit[]>(this.unitUrl,
        {
          context: new HttpContext().set(RequestAuthOption.AUTH_TOKEN, true),
          headers:this.getHttpOptions()
        })
  }
}
