import { Component, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { RecipeComponent } from '../recipe/recipe.component';
import { AuthComponent } from '../auth/auth.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { DialogRef } from '@angular/cdk/dialog';
import { User } from '../pojo/user';
import { AuthService } from '../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ThemeSwitchComponent } from '../theme-switch/theme-switch.component';

@Component({
  selector: 'app-lwd-app',
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
  templateUrl: './lwd-app.component.html',
  styleUrl: './lwd-app.component.scss'
})
export class LwdAppComponent {
}
