import { HttpClient } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Observable } from 'rxjs';
import {Component, computed, DestroyRef, effect, inject, signal} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import { Router } from '@angular/router';

type DogApiResponse = { message: string };
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, CommonModule, RouterOutlet, MatSnackBarModule, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'angular-setup';
  private readonly apiUrl = 'https://dog.ceo/api/breeds/image/random';

  readonly isLoading = signal(false);
  readonly dogImage = signal<string | null>(null);

  readonly hasDogImage = computed(() => !!this.dogImage());

  private http = inject(HttpClient)
  private destroyRef = inject(DestroyRef)
  private readonly  snackBar = inject(MatSnackBar);
  readonly router = inject(Router);

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
