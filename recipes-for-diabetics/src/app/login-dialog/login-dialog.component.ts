import { Component, EventEmitter, Input, Output } from '@angular/core';
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
import { BehaviorSubject, catchError } from 'rxjs';
import { User } from '../pojo/user';

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
  invalidLogin:boolean = false;
  hidePassword:boolean = true;

  constructor(private authService: AuthService, 
    private dialogRef: MatDialogRef<LoginDialogComponent>,
    private snackBar: MatSnackBar) {

  }
  onLogin() {
      this.authService.login(this.loginForm.controls['username'].value, 
      this.loginForm.controls['password'].value).subscribe({
        next: (response) => {
          this.invalidLogin = false;
          var user = response as User;
          console.log(user)
          this.dialogRef.close();
          this.snackBar.open("Login Successful", "Ok", {duration: 5000})
          this.authService.setCurrentUser(user);
//          this.router.navigate(["dashboard"])    
        },
        error: (err) => {
          this.invalidLogin = true;
          this.authService.setCurrentUser(null)
/*          if (err.status==403 && err.error == null) {
            this.authError.next("The user "+username+" was not found.")
            throw new Error("The user "+username+" was not found.")
          }
          else {
            var httpErr = err.error as HttpErrorResponse
            console.log(err)
            this.authError.next(httpErr.message)
            throw new Error(httpErr.message)*/
          }
        })
  }
}
