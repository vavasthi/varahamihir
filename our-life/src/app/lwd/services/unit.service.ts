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

  user: Signal<User | undefined>;
  units: WritableSignal<Map<string, Unit> | undefined> = signal(undefined);
  unitTypes: WritableSignal<Set<String>> = signal(new Set<string>(['Both']));

  constructor(private authService: AuthService,
              private snackBar: MatSnackBar,
              private httpClient: HttpClient) {
    this.user = this.authService.getCurrentUser()
    this.loadAllUnits().subscribe({
      next: (response) => {
        const allUnits:Map<string, Unit> = new Map<string, Unit>(Object.entries(response))
        const unitTypes = new Set<string>(['Both'])
        console.log("All units", typeof(allUnits), allUnits)
        Array.from(allUnits.values()).forEach( unit => {
          unitTypes.add(unit.quantityType)
        })
        this.units.set(allUnits)
        this.unitTypes.set(unitTypes)
      },
      error: (err) => {
        console.log(err)
        this.snackBar.open("Unit load failed.", "Ok", {duration: 5000})
      }
    })
  }

  getHttpOptions(): HttpHeaders {

    return new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
  }

  getUnits(): Signal<Map<string, Unit> | undefined> {
    return this.units
  }

  loadAllUnits() {

    return this
      .httpClient
      .get<Map<string, Unit>>(this.unitUrl,
        {
          context: new HttpContext().set(RequestAuthOption.AUTH_TOKEN, true),
          headers: this.getHttpOptions()
        })
  }

  getUnit(name: string|undefined): Unit {
    if (name) {

      const u = this.units()?.get(name)
      if (u) {
        return u;
      }
    }
    return {
      value: "Gram",
      acronym: "g.",
      conversionToPrimary: 1,
      unitSystem: 'Metric',
      quantityType: 'Weight',
      primary: 'Gram'
    };
  }
}
