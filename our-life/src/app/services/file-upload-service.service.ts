import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable, Signal } from '@angular/core';
import { AuthService } from './auth.service';
import { User } from '../pojo/user';
import { RequestAuthOption } from '../pojo/request-auth-option';
import { DefaultHttpHeaders } from '../pojo/default-http-headers';

@Injectable({
  providedIn: 'root'
})
export class FileUploadServiceService {

  baseUrl: string = "/api/v1";
  contentUrl: string = this.baseUrl + "/content";
  contentsUrl: string = this.baseUrl + "/content/multiple";

  constructor(private httpClient: HttpClient) {
  }

  uploadFile(file: File) {
    const formData = new FormData();
    formData.append("file", file, file.name)
    return this
      .httpClient
      .post(this.contentUrl, formData,
        {
          context: new HttpContext().set(RequestAuthOption.AUTH_TOKEN, true),
          headers:DefaultHttpHeaders.multipartHeaders
        })
  }
  uploadFiles(files: FileList) {
    const formData = new FormData();
    for (let i = 0; i < files.length; ++i) {
      formData.append("files", files[i])
    }
    return this
      .httpClient
      .post(this.contentsUrl, formData,
        {
          context: new HttpContext().set(RequestAuthOption.AUTH_TOKEN, true),
          headers:DefaultHttpHeaders.multipartHeaders
        })
  }
}
