import { Component, Input, Signal, computed, signal } from '@angular/core';
import { User } from '../pojo/user';
import { AuthService } from '../services/auth.service';
import { MatIconModule } from '@angular/material/icon';
import { CustomSidenavMenuItems } from '../pojo/custom-sidenav-menu-items';
import { MatListModule } from '@angular/material/list';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { NgClass } from '@angular/common';

@Component({
    selector: 'app-custom-sidenav',
    imports: [MatIconModule,
        MatListModule,
        RouterLink,
        RouterLinkActive,
        NgClass
    ],
    templateUrl: './custom-sidenav.component.html',
    styleUrl: './custom-sidenav.component.scss'
})
export class CustomSidenavComponent {
  sidenavCollapsed = signal(false);
  @Input() set collapsed(val: boolean) {
    this.sidenavCollapsed.set(val)
  }
  profilePicSize = computed(() => this.sidenavCollapsed() ? '32' : '100')
  @Input() menuItems:CustomSidenavMenuItems[] = []
  currentUser: Signal<User | undefined>;
  constructor(private authService: AuthService) {
    this.currentUser = this.authService.getCurrentUser()
  }
}
