import { Routes } from '@angular/router';
import { LoginDialogComponent } from './login-dialog/login-dialog.component';
import { HomeComponent } from './home/home.component';
import { LwdAppHomeComponent } from './lwd/lwd-app-home/lwd-app-home.component';
import { IngredientEditorComponent } from './lwd/ingredient-editor/ingredient-editor.component';
import { RecipeEditorComponent } from './lwd/recipe-editor/recipe-editor.component';
import { RecipeComponent } from './lwd/recipe/recipe.component';

export const routes: Routes = [
    { path:'', title: 'Our Life | Home', component: HomeComponent},
    { path:'login', title: 'Our Life | Login', component: LoginDialogComponent},
    { path: 'lwd/recipe/edit/:id', title: 'Our Life | Living with diabetes | Recipe Edit', component: RecipeEditorComponent },
    { path:'lwd/recipe/edit', title: 'Our Life | Living with diabetes | Recipe Edit', component: RecipeEditorComponent },
    { path: 'lwd/ingredient/edit/:id', title: 'Our Life | Living with diabetes | Ingredients Edit', component: IngredientEditorComponent },
    { path: 'lwd/ingredient/edit', title: 'Our Life | Living with diabetes | Ingredients Edit', component: IngredientEditorComponent },
    { path:'lwd', title: 'Our Life | Living with diabetes', component: LwdAppHomeComponent},
    { path: "test", title: "Testing Recipe", component: RecipeComponent}
];
