import { Injectable, Signal, signal } from '@angular/core';
import { HttpClient, HttpContext } from '@angular/common/http';
import { User } from '../pojo/user';
import { BehaviorSubject } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef } from '@angular/material/dialog';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { RequestAuthOption } from '../pojo/request-auth-option';
import {AuthService} from "./auth.service";
import {CanActivate, Router} from "@angular/router";
@Injectable({
  providedIn: 'root'
})
@Injectable()
export class EditorRoleGuardService implements CanActivate{
  constructor(public authService: AuthService, public router: Router) {}
  canActivate(): boolean {
    if (!this.authService.isEditor()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
