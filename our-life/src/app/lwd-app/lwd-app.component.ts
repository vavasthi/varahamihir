import { Component, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { RecipeComponent } from '../recipe/recipe.component';
import { AuthComponent } from '../auth/auth.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatDialogModule } from '@angular/material/dialog';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { ThemeSwitchComponent } from '../theme-switch/theme-switch.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTableModule } from '@angular/material/table';
import * as storedRecipe from '../../assets/defaultRecipe.json';
import { Recipe } from '../pojo/recipe';
import { RecipeService } from '../services/recipe.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
    selector: 'app-lwd-app',
    standalone: true,
    templateUrl: './lwd-app.component.html',
    styleUrl: './lwd-app.component.scss',
    imports: [CommonModule,
        RouterModule,
        RecipeComponent,
        AuthComponent,
        MatToolbarModule,
        MatIconModule,
        MatDialogModule,
        MatMenuModule,
        LoginDialogComponent,
        ThemeSwitchComponent,
        MatButtonModule,
        MatTooltipModule,
        MatTableModule]
})
export class LwdAppComponent {
  recipes:Recipe[] = [storedRecipe, storedRecipe]
  displayedColumns: string[] = ['name', 'description', 'image'];
  
  constructor(private recipeService:RecipeService, 
    private router:Router,
    private snackBar : MatSnackBar) {

    this.recipeService.getAllRecipe().subscribe({
      next: (response) => {
        this.snackBar.open("Recipes Loaded", "Ok", { duration: 5000 })
      },
      error: (err) => {
        this.snackBar.open("Recipes Load Failed", "Ok", { duration: 5000 })
      }
    })
  }

}

