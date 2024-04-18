import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiUrl } from 'environments/environment';
import { User } from 'app/data/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly url : string = apiUrl + 'user/';

  constructor(private http: HttpClient) {}

  getUser(id : string) : Observable<User> {
    return this.http.get<User>(this.url + id);
  }

  updateUser(user : User) : Observable<User> {
    return this.http.post<User>(this.url + 'update', user);
  }
}
