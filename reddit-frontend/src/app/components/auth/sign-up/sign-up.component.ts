import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SignUpRequestPayload} from "../../../../types";
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {

  signUpForm: FormGroup;
  signUpRequestPayload?: SignUpRequestPayload;

  constructor(private authService: AuthService) {
    this.signUpForm = new FormGroup({
      username: new FormControl("", [Validators.required]),
      email: new FormControl("", [Validators.required, Validators.email]),
      password: new FormControl("", [Validators.required]),
    });
  }

  ngOnInit(): void {
  }

  signup() {
    this.signUpRequestPayload = this.signUpForm.value;
    this.authService.signup(this.signUpRequestPayload).subscribe(
      value => {
        console.log(value);
      },
      error => {
        console.log("Something went wrong");
      }
    );
  }
}
