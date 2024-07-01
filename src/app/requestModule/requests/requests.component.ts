import { Component, OnDestroy, OnInit, Output} from '@angular/core';
import { CurrencyService } from '../../core/currency.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit, OnDestroy {

  @Output() isOpen = false;
  name !: string;
  currency !: string;
  requestDate !: Date;
  wartosc !: number;
  id !: number;
  index !: number;
  modalInputName !: string;
  
  
  requests: RequestsComponent[] = [];

  private subscriptions: Subscription[] = [];

  constructor(private currencyService: CurrencyService) { }

  // getRequests to metoda zdefiniowana w currencyService pobierająca dane z api wskazanego pod konkretnym aderesem, inaczej z backendu :)
  ngOnInit(): void {
   const sub =  this.currencyService.getRequests().subscribe(
      data => {
        this.requests = data;
      },
      error => {
        console.error('Error fetching requests', error);
      }
    );
    this.subscriptions.push(sub);
  }

  openModal(i: number): void {
    this.index = i;
    this.modalInputName = this.requests[this.index].name;
    this.isOpen = !this.isOpen;
  }

  deleteRequest(index: number): void {
    const requestId = this.requests[index].id;
    const sub = this.currencyService.deleteRequest(requestId).subscribe(
      () => {
        this.requests.splice(index, 1);
      },
      error => {
        console.error('Error deleting request', error);
      }
    );
    
    this.subscriptions.push(sub);

    
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
}
  
    // Subskrypcje są przechowywane w tablicy subscriptions.
    // W ngOnInit i deleteRequest każda subskrypcja jest dodawana do tej tablicy.
    // W ngOnDestroy wszystkie subskrypcje są odsubskrybowane, co zapewnia, że nie 
    // będzie wycieków pamięci, gdy komponent zostanie zniszczony.

}
