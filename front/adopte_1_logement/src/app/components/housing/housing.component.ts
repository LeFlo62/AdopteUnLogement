import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Housing } from 'app/data/housing';
import { HousingService } from 'app/services/housing.service';
import { DialogService } from 'primeng/dynamicdialog';
import { ReviewsComponent } from '../reviews/reviews.component';
import { ReviewService } from 'app/services/review.service';
import { User } from 'app/data/user';
import { TokenStorageService } from 'app/services/token-storage.service';

@Component({
  selector: 'app-housing',
  templateUrl: './housing.component.html',
  styleUrls: ['./housing.component.scss'],
  providers: [DialogService]
})
export class HousingComponent implements OnInit {
  housingData? : Housing;
  housingId!: string;

  user? : User;

  constructor(
    private route: ActivatedRoute,
    private housingService: HousingService,
    private dialogService: DialogService,
    private reviewService: ReviewService,
    private tokenStorage: TokenStorageService
  ){
    if(this.tokenStorage.isLoggedIn()) {
      this.user = this.tokenStorage.getUser();
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.housingId = params['id']
    })
    this.housingService.getHousingById(this.housingId).subscribe({
      next: (res: Housing) => {
        this.housingData = res;
        this.reviewService.getAverageRating(this.housingId).subscribe(
          (rating : {housingId : string, rating : number}) => {
            this.housingData!.rating = rating.rating;
          });
      },
      error: (err: any) => console.warn("Error occured: " + err)
    })
  }

  onShowComments(){
    this.dialogService.open(ReviewsComponent, { 
      data: {
        housingId: this.housingId,
        reviews: this.housingData!.reviews
      },
      header: 'Ratings'
    });
  }
}
