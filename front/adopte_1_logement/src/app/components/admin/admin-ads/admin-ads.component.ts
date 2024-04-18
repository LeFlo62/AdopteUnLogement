import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { Housing } from 'app/data/housing';
import { AdminService } from 'app/services/admin.service';
import { MenuItem } from 'primeng/api';
import { FileUpload } from 'primeng/fileupload';

@Component({
  selector: 'app-admin-ads',
  templateUrl: './admin-ads.component.html',
  styleUrls: ['./admin-ads.component.scss']
})
export class AdminAdsComponent {

  private static readonly PAGE_SIZE : number = 20;

  loading : boolean = true;

  housings : Housing[] = [];
  housingCount : number = 0;

  housingMenu : {[id : string] : MenuItem[]} = {};

  modalVisible : boolean = false;

  page : number = 0;

  editingHousingId : string = '';
  housingForm = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl(''),
    services: new FormArray([
      new FormControl('')
    ]),
    constraints: new FormArray([
      new FormControl('')
    ])
  });

  constructor(private adminService : AdminService) { }

  ngOnInit(): void {
    this.adminService.getHousingCount().subscribe(count => {
      this.housingCount = count;
    });

    this.loadHousings();
  }

  loadHousings() {
    this.adminService.getHousings(this.page, AdminAdsComponent.PAGE_SIZE).subscribe(housings => {
      this.loading = false;
      this.housings = housings;
      for(let housing of this.housings) {
        this.housingMenu[housing.id] = [
          {
            label: 'Modify',
            icon: 'pi pi-fw pi-pencil',
            command: () => {
              this.openModal(housing);
            }
          },
          {
            label: 'Delete',
            icon: 'pi pi-fw pi-trash',
            command: () => {
              this.deleteHousing(housing.id);
            }
          }
        ];
      }
    });
  }

  openModal(housing: Housing) {
    this.editingHousingId = housing.id;
    this.housingForm.patchValue(housing);
    this.modalVisible = true;
  }

  deleteHousing(housingId: string) {
    this.adminService.deleteHousing(housingId).subscribe(() => {
      this.loadHousings();
    });
  }

  changePage(event : any) {
    this.page = event.first/event.rows;
    this.loadHousings();
  }

  get pageSize() : number {
    return AdminAdsComponent.PAGE_SIZE;
  }

  get servicesArray(): FormArray {
    return this.housingForm.get('services') as FormArray;
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
    return this.housingForm.get('constraints') as FormArray;
  }

  addConstraint() {
    const newConstraint = new FormControl('');
    this.constraintsArray.push(newConstraint);
  }
  
  removeConstraint(index: number) {
    this.constraintsArray.removeAt(index);
  }

  updateHousing(){

    if(this.housingForm.invalid){
      return;
    }

    if(this.housingForm.get('title')?.value && this.housingForm.get('title') != null && this.housingForm.get('description')?.value && this.housingForm.get('services')?.value){

      let services : string[] = [];
      for(let service of this.housingForm.get('services')!.value){
        if(service != null && service != ''){
          services.push(service);
        }
      }

      let constraints : string[] = [];
      for(let constraint of this.housingForm.get('constraints')!.value){
        if(constraint != null && constraint != ''){
          constraints.push(constraint);
        }
      }

      let housing = {
        id: this.editingHousingId,
        title: this.housingForm.get('title')!.value!,
        description: this.housingForm.get('description')!.value!,
        services: services,
        constraints: constraints
      };

      this.adminService.updateHousing(housing).subscribe(() => {
        console.log('Housing updated');
      });
    }
  }

}
