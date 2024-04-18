import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { Validators } from '@angular/forms';
import { HousingService } from '../../services/housing.service';
import { FileUpload } from 'primeng/fileupload';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-housing',
  templateUrl: './create-housing.component.html',
  styleUrls: ['./create-housing.component.scss'],
  providers: []
})
export class CreateHousingComponent implements OnInit {
  userData: any;
  userId: string = '';
  images: string[] = [];

  createHousingFormData = new FormData();

  createHousingForm = new FormGroup({
    title: new FormControl('', Validators.required),
    housingDescription: new FormControl(''),
    services: new FormArray([
      new FormControl('')
    ]),
    constraints: new FormArray([
      new FormControl('')
    ])
  })

  constructor(
    private housingService: HousingService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const authUser: any = sessionStorage.getItem('auth-user')
    this.userData = JSON.parse(authUser);
    if(this.userData) {
      this.userId = this.userData.id;
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  // Service inputs
  get servicesArray(): FormArray {
    return this.createHousingForm.get('services') as FormArray;
  }

  addService() {
    const newService = new FormControl('');
    this.servicesArray.push(newService);
  }
  
  removeService(index: number) {
    this.servicesArray.removeAt(index);
  }

  // Constraint inputs
  get constraintsArray(): FormArray {
    return this.createHousingForm.get('constraints') as FormArray;
  }

  addConstraint() {
    const newConstraint = new FormControl('');
    this.constraintsArray.push(newConstraint);
  }
  
  removeConstraint(index: number) {
    this.constraintsArray.removeAt(index);
  }
  
  onSubmit(fileUpload : FileUpload){

    if(this.createHousingForm.invalid){
      return;
    }

    if(fileUpload.files.length == 0){
      return;
    }

      let formData : FormData = new FormData();

      formData.append('title', this.createHousingForm.get('title')?.value!);
      formData.append('description', this.createHousingForm.get('housingDescription')?.value!);
      
      for(let service of this.createHousingForm.get('services')?.value!){
        formData.append('services', service!);
      }

      for(let constraint of this.createHousingForm.get('constraints')?.value!){
        formData.append('constraints', constraint!);
      }

      for(let file of fileUpload.files){
        formData.append('images', file);
      }

      this.housingService.createHousing(formData).subscribe((id : {value : string}) => {
        this.router.navigate(['/housing/' + id.value]);
      });
  }
}
