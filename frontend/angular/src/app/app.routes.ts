import {provideRouter, Routes} from '@angular/router';
import { NotFoundComponent } from '../not-found/not-found.component';
import {HomeComponent} from "../home/home.component";

export const routes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full'},
    { path: 'home', component: HomeComponent },
    { path: 'login', loadComponent: () => import('../login/login.component').then(m => m.LoginComponent) },
    { path: '**', component: NotFoundComponent },
];