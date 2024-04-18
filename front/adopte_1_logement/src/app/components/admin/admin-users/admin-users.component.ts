import { Component, OnInit } from '@angular/core';
import { User } from 'app/data/user';
import { AdminService } from 'app/services/admin.service';
import { UserService } from 'app/services/user.service';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.scss']
})
export class AdminUsersComponent implements OnInit {

  private static readonly PAGE_SIZE : number = 20;

  loading : boolean = true;

  users : User[] = [];
  userCount : number = 0;

  userMenu : {[id : string] : MenuItem[]} = {};

  modalVisible : boolean = false;
  modalUser? : User;
  modalPassword? : string;

  page : number = 0;

  roles : any = [
    { label: 'User', value: 'USER' },
    { label: 'Admin', value: 'ADMIN' },
  ];

  constructor(private adminService : AdminService) { }

  ngOnInit(): void {
    this.adminService.getUserCount().subscribe(count => {
      this.userCount = count;
    });

    this.loadUsers();
  }

  loadUsers() {
    this.adminService.getUsers(this.page, AdminUsersComponent.PAGE_SIZE).subscribe(users => {
      this.loading = false;
      this.users = users;
      for(let user of this.users) {
        this.userMenu[user.id] = [
          {
            label: 'Modify',
            icon: 'pi pi-fw pi-pencil',
            command: () => {
              this.openModal(user);
            }
          },
          {
            label: 'Delete',
            icon: 'pi pi-fw pi-trash',
            command: () => {
              this.deleteUser(user.id);
            }
          }
        ];
      }
    });
  }

  openModal(user: User) {
    this.modalUser = user;
    this.modalPassword = '';
    this.modalVisible = true;
  }

  deleteUser(userId: string) {
    this.adminService.deleteUser(userId).subscribe(() => {
      this.loadUsers();
    });
  }

  updateUser(user : User) {
    console.log(user);
    this.adminService.updateUser(user).subscribe(() => {
      console.log('User updated');
    });
  }

  changePage(event : any) {
    this.page = event.first/event.rows;
    this.loadUsers();
  }

  get pageSize() : number {
    return AdminUsersComponent.PAGE_SIZE;
  }

}
