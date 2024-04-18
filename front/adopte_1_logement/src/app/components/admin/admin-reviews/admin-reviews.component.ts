import { Component } from '@angular/core';
import { Review } from 'app/data/housing';
import { AdminService } from 'app/services/admin.service';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-admin-reviews',
  templateUrl: './admin-reviews.component.html',
  styleUrls: ['./admin-reviews.component.scss']
})
export class AdminReviewsComponent {

  private static readonly PAGE_SIZE : number = 20;

  loading : boolean = true;

  reviews : Review[] = [];
  reviewCount : number = 0;

  reviewMenu : {[id : string] : MenuItem[]} = {};

  modalVisible : boolean = false;
  modalReview? : Review;
  modalPassword? : string;

  page : number = 0;

  roles : any = [
    { label: 'User', value: 'USER' },
    { label: 'Admin', value: 'ADMIN' },
  ];

  constructor(private adminService : AdminService) { }

  ngOnInit(): void {
    this.adminService.getReviewCount().subscribe(count => {
      this.reviewCount = count;
    });

    this.loadReviews();
  }

  loadReviews() {
    this.adminService.getReviews(this.page, AdminReviewsComponent.PAGE_SIZE).subscribe(reviews => {
      this.loading = false;
      this.reviews = reviews;
      for(let review of this.reviews) {
        this.reviewMenu[review.id] = [
          {
            label: 'Modify',
            icon: 'pi pi-fw pi-pencil',
            command: () => {
              this.openModal(review);
            }
          },
          {
            label: 'Delete',
            icon: 'pi pi-fw pi-trash',
            command: () => {
              this.deleteReview(review.id);
            }
          }
        ];
      }
    });
  }

  openModal(review: Review) {
    this.modalReview = review;
    this.modalPassword = '';
    this.modalVisible = true;
  }

  deleteReview(reviewId: string) {
    this.adminService.deleteReview(reviewId).subscribe(() => {
      this.loadReviews();
    });
  }

  updateReview(review : Review) {
    console.log(review);
    this.adminService.updateReview(review).subscribe(() => {
      console.log('Review updated');
    });
  }

  changePage(event : any) {
    this.page = event.first/event.rows;
    this.loadReviews();
  }

  get pageSize() : number {
    return AdminReviewsComponent.PAGE_SIZE;
  }

}
