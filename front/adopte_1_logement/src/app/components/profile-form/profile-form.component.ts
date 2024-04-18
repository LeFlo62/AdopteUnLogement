import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from "../../data/user";
import {UsernameValidatorDirective} from "../../directives/username-validator.directive";
import {EmailValidatorDirective} from "../../directives/email-validator.directive";
import {PasswordValidatorDirective} from "../../directives/password-validator.directive";
import {PhoneValidatorDirective} from "../../directives/phone-validator.directive";
import { UserService } from 'app/services/user.service';
import { TokenStorageService } from 'app/services/token-storage.service';

@Component({
  selector: 'app-profile-form',
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.scss']
})

export class ProfileFormComponent implements OnInit {
  
  @Input() profile!: User;
  
  emailValidator = new EmailValidatorDirective();
  passwordValidator = new PasswordValidatorDirective();
  phoneValidator = new PhoneValidatorDirective();
  
  form : FormGroup = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, this.emailValidator.validate.bind(this.emailValidator)]),
    password: new FormControl('', [this.passwordValidator.validate.bind(this.passwordValidator)]),
    passwordConfirm: new FormControl('', [this.passwordValidator.validate.bind(this.passwordValidator)]),
  });

  editing : boolean = false;

  constructor(private userService : UserService, private tokenStorage : TokenStorageService) {}

  ngOnInit() {
  }

  editForm(){
    this.editing = true;
    this.form.setValue({
      firstName: this.profile.firstName,
      lastName: this.profile.lastName,
      email: this.profile.email,
      password: '',
      passwordConfirm: ''
    });
  }

  submit() {
    if(!this.form.valid) return;

    let user : any = {
      id: this.profile.id,
      firstName: this.form.value.firstName,
      lastName: this.form.value.lastName,
      email: this.form.value.email,
      password: this.form.value.password,
    };

    this.userService.updateUser(user).subscribe((data : User) =>  {
      this.profile = data;
      this.editing = false;
    });
  }

  isSelf() : boolean{
    return this.tokenStorage.isLoggedIn() && this.profile && this.tokenStorage.getUser().id == this.profile.id;
  }

}
