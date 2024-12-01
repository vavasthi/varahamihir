import {Routes} from '@angular/router';
import {LoginDialogComponent} from './login-dialog/login-dialog.component';
import {HomeComponent} from './home/home.component';
import {LwdAppHomeComponent} from './lwd/lwd-app-home/lwd-app-home.component';
import {IngredientEditorComponent} from './lwd/ingredient-editor/ingredient-editor.component';
import {RecipeEditorComponent} from './lwd/recipe-editor/recipe-editor.component';
import {RecipeComponent} from './lwd/recipe/recipe.component';
import {HomeConfigComponent} from "./home-config/home-config.component";
import {EditorRoleGuardService} from "./services/editor-role-guard.service";

export const routes: Routes = [
  {
    path: '',
    title: 'Our Life | Home',
    component: HomeComponent
  },
  {
    path: 'login',
    title: 'Our Life | Login',
    component: LoginDialogComponent
  },
  {
    path: 'lwd/recipe/edit/:id',
    title: 'Our Life | Living with diabetes | Recipe Edit',
    component: RecipeEditorComponent,
    canActivate: [EditorRoleGuardService]
  },
  {
    path: 'lwd/recipe/edit',
    title: 'Our Life | Living with diabetes | Recipe Edit',
    component: RecipeEditorComponent,
    canActivate: [EditorRoleGuardService]
  },
  {
    path: 'lwd/ingredientEntity/edit/:id',
    title: 'Our Life | Living with diabetes | Ingredients Edit',
    component: IngredientEditorComponent,
    canActivate: [EditorRoleGuardService]
  },
  {
    path: 'lwd/ingredientEntity/edit',
    title: 'Our Life | Living with diabetes | Ingredients Edit',
    component: IngredientEditorComponent,
    canActivate: [EditorRoleGuardService]
  },
  {
    path: 'lwd',
    title: 'Our Life | Living with diabetes',
    component: LwdAppHomeComponent,
    canActivate: [EditorRoleGuardService]
  },
  {
    path: 'homepage',
    title: 'Homepage Configuration',
    component: HomeConfigComponent,
    canActivate: [EditorRoleGuardService]
  },
  {path: "test", title: "Testing Recipe", component: RecipeComponent}
];
