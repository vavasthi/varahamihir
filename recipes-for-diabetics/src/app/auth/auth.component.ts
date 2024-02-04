import { Component } from '@angular/core';
import { Authentication } from '../pojo/authentication';
import { FormsModule, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';


@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [FormsModule, 
    MatCardModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {
  username?:string;
  password?:string;
  invalidLogin:boolean = false;
  hidePassword:boolean = true;
  requiredControl = new FormControl('', [Validators.required]);
  constructor(public dialog:MatDialog) {

  }
  onLogin() {
  }
}
