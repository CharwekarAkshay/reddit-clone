import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SignUpRequestPayload} from "../../../../types";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly ROOT_URL = "api";

  constructor(private http: HttpClient) {
  }


  signup(signUpRequestPayload?: SignUpRequestPayload): Observable<string> {
    return this.http.post(this.ROOT_URL + '/auth/signup', signUpRequestPayload, {responseType: 'text'});
  }
}
