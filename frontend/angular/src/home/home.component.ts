import {Component, computed, DestroyRef, inject, signal} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {MatButtonModule} from "@angular/material/button";
import {CommonModule} from "@angular/common";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CapitalizePipe} from "../app/pipe/capitalize.pipe";

type DogApiResponse = { message: string };

@Component({
    selector: 'app-home',
    imports: [MatButtonModule, CommonModule, CapitalizePipe],
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
    private readonly apiUrl = 'https://dog.ceo/api/breeds/image/random';

    readonly isLoading = signal(false);
    readonly dogImage = signal<string | null>(null);

    readonly hasDogImage = computed(() => !!this.dogImage());

    private http = inject(HttpClient)
    private destroyRef = inject(DestroyRef)
    private readonly  snackBar = inject(MatSnackBar);

    fetchDogImage(): void {
        this.isLoading.set(true);

        const dogImage$: Observable<DogApiResponse> = this.http.get<DogApiResponse>(this.apiUrl);

        dogImage$.pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
            next: (response) => {
                this.dogImage.set(response.message);
                this.isLoading.set(false);
            },
            error: (err) => {
                this.showToast('Failed to fetch dog image');
                this.isLoading.set(false);
            },
            complete: () => {
                console.log('API call completed');
            },
        });
    }

    private showToast(message: string): void {
        this.snackBar.open(message, 'Close', {
            duration: 3000,
            horizontalPosition: 'right',
            verticalPosition: 'top',
        });
    }
}