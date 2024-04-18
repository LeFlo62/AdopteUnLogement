import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { TokenStorageService } from './services/token-storage.service';
import { User } from './data/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'adopte_1_logement';

  menuBar : MenuItem[] = [
    {label: 'Home', icon: 'pi pi-fw pi-home', routerLink: ['/home']},
  ];

  userMenu : MenuItem[] = [
    {label: 'My profile', icon: 'pi pi-fw pi-user', routerLink: ['/profile/' + this.user?.id]},
    {
      separator: true
    },
    {label: 'Log out', icon: 'pi pi-fw pi-sign-out', routerLink: ['/logout']},
  ];

  user? : User;

  constructor(private tokenStorage : TokenStorageService) {
    if(this.tokenStorage.isLoggedIn()) {
      this.user = this.tokenStorage.getUser();
      if(this.user){

        this.menuBar.push(
          {label: 'My ads', icon: 'pi pi-fw pi-tag', routerLink: ['/my-ads']},
          {label: 'Chat', icon: 'pi pi-fw pi-comment', routerLink: ['/message']},
        );

        this.userMenu[0].routerLink = ['/profile/' + this.user.id];
        
        if(this.user.roles.includes('ADMIN')) {
          this.userMenu.unshift({label: 'Back office', icon: 'pi pi-fw pi-cog', routerLink: ['/admin']});
        }
      }
    }
  }
}
