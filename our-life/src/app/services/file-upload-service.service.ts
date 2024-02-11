import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Signal } from '@angular/core';
import { AuthService } from './auth.service';
import { User } from '../pojo/user';

@Injectable({
  providedIn: 'root'
})
export class FileUploadServiceService {

  baseUrl: string = "/api/v1";
  contentUrl: string = this.baseUrl + "/content";
  user: Signal<User | undefined>;

  constructor(private httpClient: HttpClient,
    private authService: AuthService) {
    this.user = this.authService.getCurrentUser()
  }

  getHttpOptions(): HttpHeaders {

    var headers = new HttpHeaders()
    var token = 'Bearer ' + this.user()?.authToken;
    headers = headers.set('Authorization', token);
    return headers
  }

  upload(file: File) {
    const formData = new FormData();
    formData.append("file", file, file.name)
    return this
      .httpClient
      .post(this.contentUrl, formData, { headers: this.getHttpOptions() })
  }
}
