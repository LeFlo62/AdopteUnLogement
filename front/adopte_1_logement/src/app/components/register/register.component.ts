import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ValidatorService} from "../../services/validator.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {UserService} from "../../services/user.service";
import {User} from "../../data/user";
import {UsernameValidatorDirective} from "../../directives/username-validator.directive";
import {EmailValidatorDirective} from "../../directives/email-validator.directive";
import {PasswordValidatorDirective} from "../../directives/password-validator.directive";
import {PhoneValidatorDirective} from "../../directives/phone-validator.directive";

@Component({
  selector: 'app-register', templateUrl: './register.component.html', styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  isSuccessful = false;
  isSignUpFailed = false;
  submitting: boolean = false;

  usernameValidator = new UsernameValidatorDirective();
  emailValidator = new EmailValidatorDirective();
  passwordValidator = new PasswordValidatorDirective();
  phoneValidator = new PhoneValidatorDirective();


  registerForm: FormGroup = this.fb.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    password2: ['', [Validators.required]],
  }, {
    validators: [this.validatorService.equalControls('password', 'password2'),],
  });

  get loginPath() {
    return "/login";
  }


  constructor(private fb: FormBuilder, private router: Router, private validatorService: ValidatorService, private authService: AuthService, private usersService: UserService) {
  }


  isInvalid(controlName: string) {
    const control = this.registerForm.get(controlName);
    return control?.invalid && (control?.dirty || control?.touched);
  }

get phone() {
    return this.registerForm.get('phone');
}
  get firstName() {
    return this.registerForm.get('firstName');
  }

  get lastName() {
    return this.registerForm.get('lastName');
  }


  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }

  get password2() {
    return this.registerForm.get('password2');
  }

  ngOnInit(): void {
  }

  register(): void {

    console.log(this.registerForm.value);
    if (!this.registerForm.valid) {
      return;
    }

    this.submitting = true;
    const { firstName, lastName, email, password } = this.registerForm.value;
    this.authService.register(firstName, lastName, email, password).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      }, error: err => {
        console.log(err);
        this.submitting = false;
        this.isSignUpFailed = true;
      }
    });
  }
}
