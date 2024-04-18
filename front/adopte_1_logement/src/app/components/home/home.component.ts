import { Component, HostListener, OnInit } from '@angular/core';
import { HousingListItem } from '../../data/housing';
import { HousingService } from '../../services/housing.service';
import { FormControl, FormGroup } from '@angular/forms';
import { ReviewService } from 'app/services/review.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  private static readonly PAGE_SIZE : number = 20;

  housingList : HousingListItem[] = [];

  page : number = 0;
  hasElementsLeft : boolean = true;

  sortRating : any[] = [
    { label: 'Rating ascending', value: 'asc' },
    { label: 'Rating descending', value: 'desc' }
  ];

  sorting : FormGroup = new FormGroup({ rating: new FormControl() });

  constructor(private housingService : HousingService, private reviewService : ReviewService) {}

  ngOnInit(): void {
    this.loadMoreHousing();

    this.sorting.valueChanges.subscribe(data => {
      this.page = 0;
      this.hasElementsLeft = true;
      this.housingList = [];
      this.loadMoreHousing();
    });
  }

  loadMoreHousing() {
    if(this.hasElementsLeft){
      this.housingList.push(...Array(HomeComponent.PAGE_SIZE));

      let sortingStr = "";

      if(this.sorting.value.rating != null) {
        sortingStr = "rating," + this.sorting.value.rating.value;
      }

      this.housingService.getPagedHousing(this.page, HomeComponent.PAGE_SIZE, sortingStr).subscribe(
        (data) => {
          for(let i = 0; i < data.length; i++) {
            this.housingList[this.page * HomeComponent.PAGE_SIZE + i] = data[i];
          }

          this.reviewService.getAverageRatingMultiple(data.map(h => h.id)).subscribe(
            (ratings : {housingId : string, rating : number}[]) => {
              for(let i = 0; i < ratings.length; i++) {
                this.housingList.find(h => h.id == ratings[i].housingId)!.rating = ratings[i].rating;
              }
            });

          if(data.length < HomeComponent.PAGE_SIZE) {
            let diff = HomeComponent.PAGE_SIZE - data.length;
            
            //Remvove the skeletons
            this.housingList.splice(this.page * HomeComponent.PAGE_SIZE + data.length, diff);
            
            this.hasElementsLeft = false;
          }
          ++this.page;
        }
      );
    }
  }

  //Event for end of page
  @HostListener('window:scroll', ['$event'])
  onScroll(event: any) {
    if (window.innerHeight + window.scrollY >= document.getElementById('content-pane')!.offsetHeight) {
      this.loadMoreHousing();
    }
  }

}
