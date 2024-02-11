import { Injectable, Signal, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../pojo/user';
import { BehaviorSubject } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef } from '@angular/material/dialog';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private user = signal<User | undefined>(undefined);
  private currentUserKeyName = "currentUser";
  baseUrl: string = "/api/v1";
  authUrl: string = this.baseUrl + "/auth";
  refreshUrl: string = this.baseUrl + "/auth/refresh";
  httpOptions = {
    headers: { 'Content-Type': 'application/json' }
  }

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
      }, this.httpOptions)
  }
}
