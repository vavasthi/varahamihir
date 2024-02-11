import { Component } from '@angular/core';
import { RecipeService } from '../services/recipe.service';
import { Router, RouterModule } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Recipe } from '../pojo/recipe';
import { MatTableModule } from '@angular/material/table';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { ThemeSwitchComponent } from '../theme-switch/theme-switch.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { LwdAppComponent } from "../lwd-app/lwd-app.component";

@Component({
    selector: 'app-lwd-app-home',
    standalone: true,
    templateUrl: './lwd-app-home.component.html',
    styleUrl: './lwd-app-home.component.scss',
    imports: [MatTableModule,
        MatIconModule,
        MatToolbarModule,
        MatDialogModule,
        LoginDialogComponent,
        ThemeSwitchComponent,
        MatButtonModule,
        MatTooltipModule,
        RouterModule,
        MatMenuModule, 
        LwdAppComponent]
})
export class LwdAppHomeComponent {
}
