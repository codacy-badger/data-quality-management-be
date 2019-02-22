import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import {AlertService, AuthenticationService} from '../../_services';


@Component({
  selector: 'app-dashboard',
  templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  usernameMinLength = 4;
  passwordMinLength = 4;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService
  ) {
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(this.usernameMinLength)]],
      password: ['', [Validators.required, Validators.minLength(this.passwordMinLength)]],
    });

    // getAll return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to login form fields
  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    // stop here if login form is invalid
    if (this.loginForm.invalid) {
      console.log(this.loginForm.status);
      console.log(this.f.username.value + ' ' + this.f.password.value);
      return;
    }

    this.loading = true;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(resp => {
          // login successful if there's a jwt token in the response
          if (resp && resp.headers.get('Authorization')) {

            const jwt = resp.headers.get('Authorization');
            this.authenticationService.saveToken(jwt);
            this.router.navigate([this.returnUrl]);
          }
        },
        error => {
          this.alertService.error('connection error');
          this.loading = false;
          throw error;
        }
      );
  }
}
