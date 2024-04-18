import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { apiUrl } from '../../environments/environment';
import { HousingListItem, Housing } from '../data/housing';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HousingService {
    private url : string = apiUrl + 'housing';
  
  constructor(private http : HttpClient) { }
  
  public getPagedHousing(page : number, pageSize : number, sorting : string = "") : Observable<HousingListItem[]> {
    let sortString = "";
    if (sorting != "") {
      sortString = "&sort=" + sorting;
    }
    return this.http.get<HousingListItem[]>(this.url + "/list?page=" + page + "&size=" + pageSize + sortString);
  }

  public getHousingById(id: String) : Observable<Housing>{
    return this.http.get<Housing>(this.url + "/" + id);
  }

  createHousing(formData: FormData) : Observable<any> {
    return this.http.post<any>(this.url + '/create', formData, {observe: 'body'});
  }

  getHousingsByUser(userId : string) : Observable<HousingListItem[]> {
    return this.http.get<HousingListItem[]>(this.url + "/list/" + userId);
  }
}
