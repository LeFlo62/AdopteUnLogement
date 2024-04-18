import { Directive } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl, Validators } from '@angular/forms';

@Directive({
  selector: '[passwordValidator]',
  providers: [{
    provide: NG_VALIDATORS,
    useExisting: PasswordValidatorDirective,
    multi: true
  }]
})
export class PasswordValidatorDirective implements Validator {
  validate(control: AbstractControl): { [key: string]: any } | null {
    if (control.dirty) {
      return Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^\s]{8,1024}$/)(control);
    }
    return null;
  }
}
