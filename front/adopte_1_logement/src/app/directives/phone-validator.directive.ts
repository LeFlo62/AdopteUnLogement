import { Directive } from '@angular/core';
import { AbstractControl, ValidationErrors, Validator, NG_VALIDATORS } from '@angular/forms';

@Directive({
  selector: '[phoneValidator]',
  providers: [{
    provide: NG_VALIDATORS,
    useExisting: PhoneValidatorDirective,
    multi: true
  }]
})
export class PhoneValidatorDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    const regex = /^(06|07)\d{8}$/;
    const value = control.value || '';
    const isValid = regex.test(value) && value.length === 10;

    if (!isValid) {
      return { invalidPhone: true };
    }

    return null;
  }
}
