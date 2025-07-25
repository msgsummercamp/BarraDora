import { Routes } from '@angular/router';
import { NotFoundComponent } from '../not-found/not-found.component';
import { HomeComponent } from '../home/home.component';
import {authGuard} from "./guard/auth.guard";

export const routes: Routes = [
    { path: 'home', component: HomeComponent, canActivate: [authGuard] },
    { path: 'login', loadComponent: () => import('../login/login.component').then(m => m.LoginComponent) },
    { path: '**', component: NotFoundComponent },
];