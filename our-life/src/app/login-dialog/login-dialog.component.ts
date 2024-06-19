import { Component, EventEmitter, Input, Output, Signal, computed } from '@angular/core';
import { Authentication } from '../pojo/authentication';
import { FormsModule, FormControl, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import {MatSnackBar} from '@angular/material/snack-bar';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AuthService } from '../services/auth.service';
import { BehaviorSubject, catchError, filter } from 'rxjs';
import { User } from '../pojo/user';
import { ActivatedRoute, NavigationEnd, NavigationStart, Router } from '@angular/router';

@Component({
  selector: 'app-login-dialog',
  standalone: true,
  imports: [FormsModule,
    MatCardModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule],
  templateUrl: './login-dialog.component.html',
  styleUrl: './login-dialog.component.scss'
})
export class LoginDialogComponent {
  loginForm:FormGroup = new FormGroup({
    username:new FormControl(''),
    password: new FormControl('')
  })
  hidePassword:boolean = true;
  user:Signal<User|undefined>;
  previousUrl : string|undefined;
  authenticationFailed : boolean = false;

  constructor(private authService: AuthService,
    private router:Router,
    private activatedRoute:ActivatedRoute,
    private snackBar: MatSnackBar) {
      this.user = this.authService.getCurrentUser()
      console.log("Login component ", this.user)
      this.previousUrl = ""
      if (this.router.getCurrentNavigation()) {

        const state = this.router.getCurrentNavigation()?.extras.state as {
          previousUrl: string
        };

        this.previousUrl =  state.previousUrl;
      }
  }
  onLogin() {
      this.authService.login(this.loginForm.controls['username'].value,
      this.loginForm.controls['password'].value).subscribe({
        next: (response) => {
          var user = response as User;
          this.snackBar.open("Login Successful", "Ok", { duration: 5000 })
          this.authService.setCurrentUser(user);
          this.router.navigate([this.previousUrl])
          this.authenticationFailed = false;
        },
        error: (err) => {
          this.authService.setCurrentUser(undefined)
          this.authenticationFailed = true;
        }
      })
  }
  onCancel(event: any): void {
    this.router.navigate([""])
  }
}
