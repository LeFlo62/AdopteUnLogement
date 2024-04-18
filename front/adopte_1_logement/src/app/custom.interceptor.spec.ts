import { TestBed } from '@angular/core/testing';

import { CustomInterceptor } from './auth.interceptor';

describe('CustomInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      CustomInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: CustomInterceptor = TestBed.inject(CustomInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
