/* eslint-disable @typescript-eslint/adjacent-overload-signatures */
import { Component, Input, Output, EventEmitter} from '@angular/core';
import { RequestsComponent } from '../requestModule/requests/requests.component';
import { CurrencyService } from '../core/currency.service';
import { Subscription } from 'rxjs';



@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent{

  @Input() title!: string;
  @Input() isOpen = false;
  @Output() closed = new EventEmitter<void>();
  newName !: string;
  name = "name";

  private subscriptions: Subscription[] = [];
  
  constructor(private requests: RequestsComponent, private currencyService: CurrencyService){}


  closeModal(): void { 
    this.requests.isOpen = false;
  }

  editRequest() { 

    const requestId = this.requests.requests[this.requests.index].id;
    const sub = this.currencyService.patchRequest(requestId, this.newName).subscribe(
      (Response) => {
         console.log('Response:', Response);
        // Aktualizacja imienia w widoku
        this.requests.requests[this.requests.index].name = this.newName;
        // czyszczenie pola input
        this.newName = '';
        
      },
      (error: unknown) => {
        console.error('Error editing request', error);
      }
    );
    this.subscriptions.push(sub);
  }


}
