import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-not-found',
    imports: [CommonModule],
    templateUrl: './not-found.component.html',
    styleUrl: './not-found.component.scss',
})
export class NotFoundComponent {
    constructor(private router: Router) {}

    isNotMainRoute(): boolean {
        return this.router.url !== '/';
    }
}