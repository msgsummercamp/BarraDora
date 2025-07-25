import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import {
  provideHttpClient,
  withInterceptorsFromDi,
  withInterceptors,
} from '@angular/common/http';
import {provideRouter} from "@angular/router";
import {routes} from "./app/app.routes";
import {authInterceptor} from "./app/interceptor/auth.interceptor";

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [provideHttpClient(withInterceptors([authInterceptor])), provideRouter(routes)],
}).catch((err) => console.error(err));
