import { Component, Input, OnInit } from '@angular/core';
import { Review } from 'app/data/housing';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent implements OnInit{
  @Input() review : Review | any;
  @Input() minimal : boolean = false;
  date: Date | any;
  dateString: string = '';

  constructor(){}

  ngOnInit(): void {
    this.date = new Date(Number(this.review.time));
    this.dateString = this.date.toISOString();
  }

}
