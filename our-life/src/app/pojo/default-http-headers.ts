import { HttpHeaders } from "@angular/common/http";

export class DefaultHttpHeaders {
  public static headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  public static multipartHeaders = new HttpHeaders();
}
