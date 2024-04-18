import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Message } from '../data/message';
import { apiUrl } from 'environments/environment';
import { Observable } from 'rxjs';
import { User } from '../data/user';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private url : string = apiUrl + "messages";

  constructor(private http : HttpClient) { }

  getMessages(id : string, page : number, pageSize : number) : Observable<Message[]> {
    return this.http.get<Message[]>(this.url + "/" + id + "?page=" + page + "&size=" + pageSize);
  }

  sendMessage(id : string, content : string) : Observable<any>{
    return this.http.post<any>(this.url + '/' + id + '/send', content);
  }

  getLastUsers() : Observable<User[]>{
    return this.http.get<User[]>(this.url + '/lastUsers');
  }

}
