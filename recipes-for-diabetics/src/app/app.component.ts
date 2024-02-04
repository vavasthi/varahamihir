import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { RecipeComponent } from './recipe/recipe.component';
import { AuthComponent } from './auth/auth.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { LoginDialogComponent } from './login-dialog/login-dialog.component';
import { DialogRef } from '@angular/cdk/dialog';
import { User } from './pojo/user';
import { AuthService } from './services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ThemeSwitchComponent } from './theme-switch/theme-switch.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule,
    RouterOutlet,
    RecipeComponent,
    AuthComponent,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    MatMenuModule,
    LoginDialogComponent,
    ThemeSwitchComponent,
    MatButtonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Recipes for Diebetes';
  currentUser:User|null = null;
  constructor(private dialog: MatDialog,
    private authService:AuthService,
    private snackBar:MatSnackBar) {
    this.authService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
    })
  }
  login() {
    const dialogRef = this.dialog.open(LoginDialogComponent)
    dialogRef.afterClosed().subscribe(result => {
      dialogRef.close();
    })
  }
  logout() {
    this.snackBar.open("Loggging out..", "Ok", {duration: 5000})
    this.authService.setCurrentUser(null)
  }
}
