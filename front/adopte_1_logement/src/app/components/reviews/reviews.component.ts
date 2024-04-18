import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Review } from 'app/data/housing';
import { ReviewService } from 'app/services/review.service';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-reviews-dialog',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {
    
  housingId: string = '';
  reviews: Review[] | undefined;
  newReviewContent: string = '';
  newReviewRating: number = 0;
  authUser: any;
  userId: string = '';
  userName: string = '';
  isAbleToComment: boolean = false;
  isClicked: boolean = false;
     
  constructor( 
    private ref: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private router: Router,
    private reviewService: ReviewService
  ) {}

  ngOnInit(){
    this.housingId = this.config.data.housingId;
    this.reviewService.getReviewsByHousing(this.housingId).subscribe({
      next: (res: any) => this.reviews = res.reverse(),
      error: () => console.warn('Error while getting reviews')
    });
    this.authUser = sessionStorage.getItem("auth-user");
    this.userId = JSON.parse(this.authUser).id;
    this.userName = `${JSON.parse(this.authUser).firstName} ${JSON.parse(this.authUser).lastName}`
  }

  onLoginVerification() {
    const userToken = sessionStorage.getItem('auth-token');
    console.warn(userToken)
    if(userToken===null){
      this.router.navigate(['/login']);
      this.ref.close();
    } else {
      this.isAbleToComment = true;
      this.isClicked = true;
    }
  }

  onPostReview() {
    const time: Date = new Date();
    console.log(time);
    console.log(this.housingId)
    this.reviewService.addReview(this.housingId, this.newReviewContent, this.newReviewRating).subscribe({
      next: () => {
        const newReview: Review = {
          id: '',
          authorId: '',
          authorName: this.userName,
          time: new Date(),
          housingId: '',
          content: this.newReviewContent,
          rating: this.newReviewRating
        }
        this.reviews?.unshift(newReview);
        this.newReviewContent = '';
        this.newReviewRating = 0;
      },
      error: () => console.warn("Error occured in onPostReview function, couldn't post review")
    })
  }
}
