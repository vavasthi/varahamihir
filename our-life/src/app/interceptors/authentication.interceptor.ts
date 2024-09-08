import { HttpErrorResponse, HttpEvent, HttpEventType, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { RequestAuthOption } from '../pojo/request-auth-option';


export class AuthenticationInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>,
    handler: HttpHandler): Observable<HttpEvent<any>> {
      console.log("In interceptor request = ", req)
    var authService = inject(AuthService)
    if (req.context.get(RequestAuthOption.NONE)) {

      return handler.handle(req).pipe(
        catchError((error: HttpErrorResponse) => {

          return throwError(() => error)
        })
      );
    }
    else if (req.context.get(RequestAuthOption.REFRESH_TOKEN) == true) {

      var authToken = 'Bearer ' + authService.getCurrentUser()()?.refreshToken
      var newHeaders = req.headers.append('Authorization', authToken).append("Content-Type", "application/json");
      req.context.set(RequestAuthOption.REFRESH_TOKEN, false);
      return handler.handle(req.clone({headers: newHeaders, context:req.context}));
    }
    else if (req.context.get(RequestAuthOption.AUTH_TOKEN)) {
      var authToken = 'Bearer ' + authService.getCurrentUser()()?.authToken
      var newHeaders = req.headers.append('Authorization', authToken)
      var originalRequest = req.clone();
      return handler.handle(req.clone({ headers: newHeaders })).pipe(
        catchError((error: HttpErrorResponse) => {

          console.log("First request failed.", error)
          if (error.status == 403) {
            authService.refresh();
            originalRequest.context.set(RequestAuthOption.AUTH_TOKEN, true);
            var authToken = 'Bearer ' + authService.getCurrentUser()()?.authToken
            var newHeaders = originalRequest.headers.append('Authorization', authToken)
            return handler.handle(originalRequest.clone({ headers: newHeaders, context:originalRequest.context }));
          }
          return throwError(() => error)
        })
      );
    }
    else {
      return handler.handle(req);
    }
  }
}
