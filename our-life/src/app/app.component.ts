import { Component, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { RecipeComponent } from './lwd/recipe/recipe.component';
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
import { MainMenuBarComponent } from './main-menu-bar/main-menu-bar.component';
import { HomeComponent } from './home/home.component';
import { CustomSidenavMenuItems } from './pojo/custom-sidenav-menu-items';

@Component({
    selector: 'app-root',
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
        MatButtonModule,
        MainMenuBarComponent,
        HomeComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Recipes for Diebetes';
  currentUser: Signal<User | undefined>;
  sideNavMenu: CustomSidenavMenuItems[] = [
    {
      icon:  'home',
      label: 'Home',
      route: ""
    },
    {
      icon:  'skillet_cooktop',
      label: 'Recipes',
      route: "lwd"
    }
  ]
  constructor(private dialog: MatDialog,
    private authService: AuthService,
    private snackBar: MatSnackBar) {
    this.currentUser = this.authService.getCurrentUser()
  }
}
