import {Component, computed, Input, Signal, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute, Router, RouterOutlet} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatMenuModule} from '@angular/material/menu';
import {MatDialogModule} from '@angular/material/dialog';
import {User} from '../pojo/user';
import {AuthService} from '../services/auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ThemeSwitchComponent} from '../theme-switch/theme-switch.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import {CustomSidenavComponent} from "../custom-sidenav/custom-sidenav.component";
import {CustomSidenavMenuItems} from '../pojo/custom-sidenav-menu-items';

@Component({
    selector: 'app-main-menu-bar',
    templateUrl: './main-menu-bar.component.html',
    styleUrl: './main-menu-bar.component.scss',
  imports: [CommonModule,
    RouterOutlet,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    MatMenuModule,
    ThemeSwitchComponent,
    MatSidenavModule,
    MatButtonModule, CustomSidenavComponent]
})
export class MainMenuBarComponent {
  currentUser: Signal<User | undefined>;
  currentUrl = ""
  @Input() menuItems: CustomSidenavMenuItems[] = []
  sidenavCollapsed = signal(false)
  sidenavWidth = computed(() => this.sidenavCollapsed() ? '65px' : '250px')

  constructor(private activatedRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private snackBar: MatSnackBar) {

    this.currentUser = this.authService.getCurrentUser()
    this.currentUrl = ""
    if (this.router.getCurrentNavigation()) {

      const state = this.router.getCurrentNavigation()?.extras.state as {
        previousUrl: string
      };

      this.currentUrl = state?.previousUrl;
    }
  }

  login() {
    this.router.navigate(["login"], { state: { "previousUrl": this.currentUrl } })
  }
  logout() {
    this.authService.clearCurrentUser()
    this.snackBar.open("Loggging out..", "Ok", { duration: 5000 })
    this.router.navigate([""], { state: { "previousUrl": this.currentUrl } })
  }
}
