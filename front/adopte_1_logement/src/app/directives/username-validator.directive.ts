import { Directive } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[usernameValidator][ngModel]',
})
export class UsernameValidatorDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    const regex = /^[a-zA-Z0-9_]*$/;
    const value = control.value || '';
    const isValid = regex.test(value) && value.length >= 3 && value.length <= 20;

    if (!isValid) {
      return { invalidUsername: true };
    }

    return null;
  }


}

