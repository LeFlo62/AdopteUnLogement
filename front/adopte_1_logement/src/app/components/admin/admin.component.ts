import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'app/services/token-storage.service';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  items!: MenuItem[];

  activeItem!: MenuItem;

  constructor(private router : Router, private tokenStorage : TokenStorageService) {

  }

  ngOnInit() {
    if (!this.tokenStorage.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }

    
    if (!this.tokenStorage.getUser().roles.includes('ADMIN')) {
      this.router.navigate(['/home']);
      return;
    }

      this.items = [
          { label: 'Users', icon: 'pi pi-fw pi-user' },
          { label: 'Ads', icon: 'pi pi-fw pi-home' },
          { label: 'Ratings', icon: 'pi pi-fw pi-star' },
      ];

      this.activeItem = this.items[0];
  }

}
