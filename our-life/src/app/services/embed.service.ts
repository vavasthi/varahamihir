import { Injectable, Signal } from '@angular/core';
import { User } from '../pojo/user';
import { AuthService } from './auth.service';
import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { PageableEquation } from '../pojo/pageable-equation';
import { RequestAuthOption } from '../pojo/request-auth-option';
import {PageableEmbed} from "../pojo/pageable-embed";
import {Embed} from "../pojo/embed";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EmbedService {

  baseUrl: string = "/api/v1";
  embedUrl: string = this.baseUrl + "/embed";
  embedAllUrl: string = this.embedUrl + "/0/100";
  user: Signal<User|undefined>;

  constructor(private authService: AuthService,
    private httpClient:HttpClient) {
      this.user = this.authService.getCurrentUser()
    }
  getHttpOptions():HttpHeaders {

    return new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
    }

  getAllEmbeds() {

    return this
      .httpClient
      .get<PageableEmbed>(this.embedAllUrl,
        {
          context: new HttpContext().set(RequestAuthOption.NONE, true),
          headers:this.getHttpOptions()
      })
  }
  createEmbed(code:string):Observable<Embed> {

    return this
      .httpClient
      .post<Embed>(this.embedUrl,
        {
          'code': code
        },
        {
          context: new HttpContext().set(RequestAuthOption.AUTH_TOKEN, true),
          headers:this.getHttpOptions()
        })
  }
}
