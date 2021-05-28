import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginRequestPayload } from 'src/types';
import { AuthService } from '../shared/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginRequestPayload?: LoginRequestPayload;


  constructor(private authService: AuthService) {
    this.loginForm = new FormGroup({
      username: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required]),
    });
  }

  ngOnInit(): void {
  }

  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAsTouched();
    } else {
      this.loginRequestPayload = this.loginForm.value;
      this.authService.login(this.loginRequestPayload);
    }
  }

}
