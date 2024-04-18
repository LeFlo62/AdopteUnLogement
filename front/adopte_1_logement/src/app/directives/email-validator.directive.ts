import {Directive} from "@angular/core";
import {AbstractControl, NG_VALIDATORS, ValidationErrors, Validator} from "@angular/forms";

@Directive({
  selector: '[emailValidator][ngModel]',
  providers: [
    {provide: NG_VALIDATORS, useExisting: EmailValidatorDirective, multi: true}
  ]
})
export class EmailValidatorDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const value = control.value || '';
    const isValid = regex.test(value);

    if (!isValid) {
      return {invalidEmail: true};
    }

    return null;
  }
}
