import { HttpClient } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Observable } from 'rxjs';
import { Component, computed, DestroyRef, effect, signal } from '@angular/core';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'angular-setup';
  private readonly apiUrl = 'https://dog.ceo/api/breeds/image/random';

  isLoading = signal(false);
  dogImage = signal<string | null>(null);

  hasDogImage = computed(() => !!this.dogImage());

  constructor(
    private http: HttpClient,
    private destroyRef: DestroyRef,
  ) {
    effect(() => {
      console.log('Dog image updated:', this.dogImage());
    });
  }

  fetchDogImage(): void {
    this.isLoading.set(true);

    const dogImage$: Observable<{ message: string }> = this.http.get<{
      message: string;
    }>(this.apiUrl);

    dogImage$.pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
      next: (response) => {
        console.log('API response:', response);
        this.dogImage.set(response.message);
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('API error:', err);
        alert('Failed to fetch dog image. Please try again later.');
        this.isLoading.set(false);
      },
      complete: () => {
        console.log('API call completed');
      },
    });
  }
}
