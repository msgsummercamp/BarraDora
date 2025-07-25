import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const mockToken = 'mock-token-12345';

    if (req.url.includes('https://dog.ceo/api')) {
        return next(req);
    }

    const cloned = req.clone({
        setHeaders: {
            Authorization: `Bearer ${mockToken}`,
        },
    });
    return next(cloned);
};