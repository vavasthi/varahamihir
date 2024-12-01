import {Component, Signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterOutlet} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatMenuModule} from '@angular/material/menu';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {User} from './pojo/user';
import {AuthService} from './services/auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MainMenuBarComponent} from './main-menu-bar/main-menu-bar.component';
import {CustomSidenavMenuItems} from './pojo/custom-sidenav-menu-items';

@Component({
    selector: 'app-root',
  imports: [CommonModule,
    RouterOutlet,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    MatMenuModule,
    MatButtonModule,
    MainMenuBarComponent],
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
    },
    {
      icon:  'newsstand',
      label: 'Articles',
      route: "homepage"
    }
  ]
  constructor(private dialog: MatDialog,
    private authService: AuthService,
    private snackBar: MatSnackBar) {
    this.currentUser = this.authService.getCurrentUser()
  }
}
