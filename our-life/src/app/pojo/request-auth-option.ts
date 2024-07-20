import { HttpContextToken } from "@angular/common/http";
export class RequestAuthOption {
  static readonly AUTH_TOKEN = new HttpContextToken<boolean>(() => false);
  static readonly REFRESH_TOKEN = new HttpContextToken<boolean>(() => false);
  static readonly NONE = new HttpContextToken<boolean>(() => false);
}
