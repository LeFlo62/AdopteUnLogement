import { Component, Input } from '@angular/core';
import { HousingListItem } from 'app/data/housing';

@Component({
  selector: 'housing-list-item',
  templateUrl: './housing-list-item.component.html',
  styleUrls: ['./housing-list-item.component.scss']
})
export class HousingListItemComponent {

  @Input() housing!: HousingListItem;

  @Input() minimal : boolean = false;

}
