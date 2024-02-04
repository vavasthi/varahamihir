import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../pojo/user';
import { BehaviorSubject } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userBS = new BehaviorSubject<User | null>(null);
  baseUrl: string = "/api/v1";
  authUrl: string = this.baseUrl + "/auth";
  refreshUrl: string = this.baseUrl + "/auth/refresh";
  httpOptions = {
    headers: { 'Content-Type': 'application/json' }
  }

  constructor(private httpClient: HttpClient, private snackBar:MatSnackBar) {     
    var user = localStorage.getItem("currentUser") as User;
    this.userBS.next(user)
}
  getCurrentUser(): BehaviorSubject<User | null> {
    return this.userBS;
  }
  setCurrentUser(user:User|null) {
    this.userBS.next(user);
    if (user) {

      localStorage.setItem("currentUser", JSON.stringify(user))
    }
    else {
      localStorage.removeItem("currentUser")
    }
  }
  login(username?: string, password?: string) {

      return this
      .httpClient
      .post<User>(this.authUrl, {
        'username': username,
        'password': password
      }, this.httpOptions);
    }
}
