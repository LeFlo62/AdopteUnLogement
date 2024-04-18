import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'app/services/token-storage.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent {

  constructor(private tokenStorage : TokenStorageService, private router : Router){
    this.tokenStorage.signOut();
    this.router.navigate(['/home']).then(() => {
      window.location.reload();
    });
  }

}
