import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginRequestPayload } from 'src/types';
import { AuthService } from '../shared/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loginRequestPayload?: LoginRequestPayload;
  registeredSuccessMessage?: string;

  isError: boolean = false;

  constructor(
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private toasterService: ToastrService,
    private router: Router,
  ) {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });

    this.activatedRoute.queryParams.subscribe((params) => {
      if (params.registered != undefined && params.registered === true) {
        this.toasterService.success('Signup Successful');
        this.registeredSuccessMessage =
          'Account creation successful please check your mailbox to activate your account';
      }
    });
  }

  ngOnInit(): void {}

  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAsTouched();
    } else {
      this.loginRequestPayload = this.loginForm.value;
      this.authService.login(this.loginRequestPayload).subscribe(
        (data) => {
          this.isError = false;
          this.router.navigateByUrl('/');
          this.toasterService.success("Login Successful");
        },
        (error) => (this.isError = true)
      );
    }
  }
}
