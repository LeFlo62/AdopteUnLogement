import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { HousingComponent } from './components/housing/housing.component';
import { AdsComponent } from './components/ads/ads.component';
import { CreateHousingComponent } from './components/create-housing/create-housing.component';
import { ProfileComponent } from "./components/profile/profile.component";
import { LoginComponent } from "./components/login/login.component";
import { RegisterComponent } from "./components/register/register.component";
import { LogoutComponent } from './components/logout/logout.component';
import { MessengerComponent } from './components/messenger/messenger.component';
import { AdminComponent } from './components/admin/admin.component';
import { LocationStrategy, PathLocationStrategy } from '@angular/common';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'housing/:id', component: HousingComponent },
  { path: 'my-ads', component: AdsComponent },
  { path: 'create-housing', component: CreateHousingComponent },
  { path: 'profile/:id', component: ProfileComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'message', component: MessengerComponent },
  { path: 'message/:id', component: MessengerComponent},
  { path: 'admin', component: AdminComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
