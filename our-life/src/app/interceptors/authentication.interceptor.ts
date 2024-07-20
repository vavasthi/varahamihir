import { HttpErrorResponse, HttpEvent, HttpEventType, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { RequestAuthOption } from '../pojo/request-auth-option';


export class AuthenticationInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>,
    handler: HttpHandler): Observable<HttpEvent<any>> {
    console.log('Request URL: ' + req.url);
    var authService = inject(AuthService)
    if (req.context.get(RequestAuthOption.NONE)) {

      console.log("A NONE request received..");
      return handler.handle(req).pipe(
        catchError((error: HttpErrorResponse) => {

          return throwError(() => error)
        })
      );
    }
    else if (req.context.get(RequestAuthOption.REFRESH_TOKEN) == true) {

      var authToken = 'Bearer ' + authService.getCurrentUser()()?.refreshToken
      var newHeaders = req.headers.append('Authorization', authToken).append("Content-Type", "application/json");
      console.log("A refresh token request received.. with token " + authToken);
      req.context.set(RequestAuthOption.REFRESH_TOKEN, false);
      return handler.handle(req.clone({headers: newHeaders, context:req.context}));
    }
    else if (req.context.get(RequestAuthOption.AUTH_TOKEN)) {
      var authToken = 'Bearer ' + authService.getCurrentUser()()?.authToken
      var newHeaders = req.headers.append('Authorization', 'Bearer ' + authToken)
      console.log("A regular auth token request received.. with auth token " + authToken);
      var originalRequest = req.clone();
      return handler.handle(req.clone({ headers: newHeaders })).pipe(
        catchError((error: HttpErrorResponse) => {

          if (error.status == 403) {
            console.log("Looks like auth token expired.. refreshing token..");
            authService.refresh();
            originalRequest.context.set(RequestAuthOption.AUTH_TOKEN, true);
            var authToken = 'Bearer ' + authService.getCurrentUser()()?.authToken
            var newHeaders = originalRequest.headers.append('Authorization', authToken)
            console.log("Retrying original regular auth token request with refreshed authtoken.. with auth token " + authToken + " " + originalRequest.url);
            return handler.handle(originalRequest.clone({ headers: newHeaders, context:originalRequest.context }));
          }
          return throwError(() => error)
        })
      );
    }
    else {
      console.log("A default request received..");
      return handler.handle(req);
    }
  }
}
