import { ApplicationConfig, ErrorHandler, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter, withDisabledInitialNavigation } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { authInterceptor } from './core/interceptors/auth.interceptor';
import { SilentErrorHandler } from './core/error-handler';

// Configuração global de bootstrap da aplicação Angular.
export const appConfig: ApplicationConfig = {
  providers: [
    // Registra tratamento global de erros em runtime.
    provideBrowserGlobalErrorListeners(),
    { provide: ErrorHandler, useClass: SilentErrorHandler },
    // Habilita o roteador com as rotas definidas em app.routes.ts.
    provideRouter(routes, withDisabledInitialNavigation()),
    // Habilita HttpClient com interceptor de JWT.
    provideHttpClient(withInterceptors([authInterceptor])),
  ]
};
