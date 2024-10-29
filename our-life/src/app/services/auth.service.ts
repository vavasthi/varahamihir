import { Injectable, Signal, signal } from '@angular/core';
import { HttpClient, HttpContext } from '@angular/common/http';
import { User } from '../pojo/user';
import { BehaviorSubject } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef } from '@angular/material/dialog';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { RequestAuthOption } from '../pojo/request-auth-option';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private user = signal<User | undefined>(undefined);
  private currentUserKeyName = "currentUser";
  baseUrl: string = "/api/v1";
  authUrl: string = this.baseUrl + "/auth";
  refreshUrl: string = this.baseUrl + "/auth/refresh";

  constructor(private httpClient: HttpClient, private snackBar: MatSnackBar) {
    var storedUserJson = localStorage.getItem(this.currentUserKeyName);
    if (storedUserJson != null) {

      try {

        var user = JSON.parse(storedUserJson) as User;
        this.user.set(user)
        console.log(user)
      }
      catch(e) {
        localStorage.removeItem(this.currentUserKeyName);
        this.user.set(undefined)
      }
    }
  }
  getCurrentUser(): Signal<User | undefined> {
    return this.user;
  }
  setCurrentUser(user: User | undefined) {
    localStorage.setItem(this.currentUserKeyName, JSON.stringify(user))
    this.user.set(user);
  }
  clearCurrentUser() {
    localStorage.removeItem(this.currentUserKeyName)
    this.user.set(undefined);
  }
  login(username?: string, password?: string) {

    return this
      .httpClient
      .post<User>(this.authUrl, {
        'username': username,
        'password': password
      },
      {
        context: new HttpContext().set(RequestAuthOption.NONE, true),
        headers: { 'Content-Type': 'application/json' }
      })
  }
  refresh() {

    var refreshHttpOptions = {
      headers: { 'Content-Type': 'application/json'},
      context: new HttpContext().set(RequestAuthOption.REFRESH_TOKEN, true),
    }
      return this
      .httpClient
      .post<User>(this.refreshUrl, {}, refreshHttpOptions)
      .subscribe({
        next: (response) => {

          var user = response as User;
          this.setCurrentUser(user);
          this.snackBar.open("Auth Token refreshed", "Ok", { duration: 5000 })
        },
        error: (err) => {
          console.log(err)
          this.setCurrentUser(undefined)
          this.snackBar.open("Auth Token refresh failed", "Ok", { duration: 5000 })
        }
      })
  }
}
