import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { LoginRequestPayload, LoginResponsePayload, SignUpRequestPayload } from "../../../../types";
import { Observable, of } from "rxjs";
import { LocalStorageService } from 'ngx-webstorage';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly ROOT_URL = "api/auth";

  constructor(private http: HttpClient, private localStorage: LocalStorageService) {
  }


  signup(signUpRequestPayload?: SignUpRequestPayload): Observable<string> {
    if (!signUpRequestPayload) of();
    return this.http.post(this.ROOT_URL + '/signup', signUpRequestPayload, { responseType: 'text' });
  }

  login(loginRequestPayload?: LoginRequestPayload): Observable<boolean> {
    if (!loginRequestPayload) of();

    return this.http.post<LoginResponsePayload>(this.ROOT_URL + '/login', loginRequestPayload)
      .pipe(
        map(data => {
          this.localStorage.store('authenticationToken', data.authenticationToken);
          this.localStorage.store('username', data.username);
          this.localStorage.store('refreshToken', data.refreshToken);
          this.localStorage.store('expiresAt', data.expiresAt);
          return true;
        })
      );
  }
}
