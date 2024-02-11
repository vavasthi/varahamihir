import { Component } from '@angular/core';
import { MainMenuBarComponent } from "../main-menu-bar/main-menu-bar.component";
import { CustomSidenavMenuItems } from '../pojo/custom-sidenav-menu-items';

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
    imports: [MainMenuBarComponent]
})
export class HomeComponent {

}
