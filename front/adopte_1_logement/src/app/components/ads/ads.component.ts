import { Component, HostListener } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { HousingListItem } from 'app/data/housing';
import { HousingService } from 'app/services/housing.service';
import { ReviewService } from 'app/services/review.service';
import { HomeComponent } from '../home/home.component';
import { TokenStorageService } from 'app/services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ads',
  templateUrl: './ads.component.html',
  styleUrls: ['./ads.component.scss']
})
export class AdsComponent {

  private static readonly PAGE_SIZE : number = 20;

  housingList : HousingListItem[] = [];

  userId? : string;

  constructor(private router : Router, private housingService : HousingService, private reviewService : ReviewService, private tokenStorage : TokenStorageService) {}

  ngOnInit(): void {
    if(!this.tokenStorage.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }

    this.userId = this.tokenStorage.getUser().id;

    this.loadMoreHousing();
  }

  loadMoreHousing() {
    if(this.userId){
      this.housingService.getHousingsByUser(this.userId).subscribe(
        (data) => {
          this.housingList = data;
  
          this.reviewService.getAverageRatingMultiple(data.map(h => h.id)).subscribe(
            (ratings : {housingId : string, rating : number}[]) => {
              for(let i = 0; i < ratings.length; i++) {
                this.housingList.find(h => h.id == ratings[i].housingId)!.rating = ratings[i].rating;
              }
            });
        }
      );
    }
  }

}
