import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SignUpRequestPayload } from '../../../../types';
import { AuthService } from '../shared/auth.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss'],
})
export class SignUpComponent implements OnInit {
  signUpForm: FormGroup;
  signUpRequestPayload?: SignUpRequestPayload;

  constructor(
    private authService: AuthService,
    private toasterService: ToastrService,
    private router: Router
  ) {
    this.signUpForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {}

  signup() {
    this.signUpRequestPayload = this.signUpForm.value;
    this.authService.signup(this.signUpRequestPayload).subscribe(
      (value) => {
        this.router.navigate(['/login'], { queryParams: { registered: true } });
      },
      (error) => {
        this.toasterService.error('Registration failed please try again later');
      }
    );
  }
}
