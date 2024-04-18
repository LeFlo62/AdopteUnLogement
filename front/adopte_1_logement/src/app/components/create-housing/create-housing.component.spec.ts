import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateHousingComponent } from './create-housing.component';

describe('CreateHousingComponent', () => {
  let component: CreateHousingComponent;
  let fixture: ComponentFixture<CreateHousingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateHousingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateHousingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
