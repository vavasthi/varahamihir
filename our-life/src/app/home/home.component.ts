import { Component } from '@angular/core';
import { MainMenuBarComponent } from "../main-menu-bar/main-menu-bar.component";
import { CustomSidenavMenuItems } from '../pojo/custom-sidenav-menu-items';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import {MatSnackBar} from '@angular/material/snack-bar';
import { FormsModule, FormControl, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
    imports: [MainMenuBarComponent, 
        MatCardModule, 
        MatIconModule, 
        MatButtonModule, 
        MatInputModule,
        ReactiveFormsModule, 
        FormsModule]
})
export class HomeComponent {

    testForm:FormGroup = new FormGroup({
        input:new FormControl('Username Initial Value')
      })
      onTest() {
      }

}
