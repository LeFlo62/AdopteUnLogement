import { Component } from '@angular/core';
import { User } from "../../data/user";
import { UserService } from 'app/services/user.service';
import { ActivatedRoute, Route } from '@angular/router';
import { TokenStorageService } from 'app/services/token-storage.service';
import { HousingService } from 'app/services/housing.service';
import { ReviewService } from 'app/services/review.service';
import { HousingListItem, Review } from 'app/data/housing';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  reviews : Review[] = [];
  locations : HousingListItem[] = [];
  profile!: User;

  constructor(private route : ActivatedRoute, private userService : UserService, private tokenService : TokenStorageService, private reviewService : ReviewService, private housingService : HousingService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userService.getUser(params['id']).subscribe(user => {
        this.profile = user;
      });

      this.housingService.getHousingsByUser(params['id']).subscribe((housings : HousingListItem[]) => {
        this.locations = housings;
        this.reviewService.getAverageRatingMultiple(housings.map(h => h.id)).subscribe(
          (ratings : {housingId : string, rating : number}[]) => {
            for(let i = 0; i < ratings.length; i++) {
              this.locations.find(h => h.id == ratings[i].housingId)!.rating = ratings[i].rating;
            }
          });
      });

      this.reviewService.getReviewsByUser(params['id']).subscribe((reviews : Review[]) => {
        this.reviews = reviews;
      });
    });
  }
}
