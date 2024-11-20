import { Component, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { RecipeComponent } from '../recipe/recipe.component';
import { AuthComponent } from '../../auth/auth.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatDialogModule } from '@angular/material/dialog';
import { LoginDialogComponent } from '../../login-dialog/login-dialog.component';
import { ThemeSwitchComponent } from '../../theme-switch/theme-switch.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTableModule } from '@angular/material/table';
import * as storedRecipe from '../../../assets/defaultRecipe.json';
import { Recipe } from '../pojo/recipe';
import { RecipeService } from '../services/recipe.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-lwd-app',
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
    private authService: AuthService,
    private router:Router,
    private snackBar : MatSnackBar) {
      this.loadRecipes(false)
  }
  private loadRecipes(retrying:boolean) {

    this.recipeService.getAllRecipe().subscribe({
      next: (response) => {
        this.snackBar.open("Recipes Loaded", "Ok", { duration: 5000 })
      },
      error: (err) => {
        if (err.status == 403 && !retrying) {
          this.authService.refresh();
          this.loadRecipes(true);
        }
        else {

          console.log(err)
          this.snackBar.open("Recipes Load Failed", "Ok", { duration: 5000 })
          }
      }
    })
  }

}

