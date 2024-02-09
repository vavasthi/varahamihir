import { Routes } from '@angular/router';
import { LoginDialogComponent } from './login-dialog/login-dialog.component';
import { LwdAppComponent } from './lwd-app/lwd-app.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
    { path:'', component: HomeComponent},
    { path:'login', component: LoginDialogComponent},
    { path:'lwd', component: LwdAppComponent}
];
