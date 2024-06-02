import { Component, OnInit } from '@angular/core';
import { CurrencyService } from '../currency.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {
  requests: any[] = [];

  constructor(private currencyService: CurrencyService) { }

  ngOnInit(): void {
    this.currencyService.getRequests().subscribe(
      data => {
        this.requests = data;
      },
      error => {
        console.error('Error fetching requests', error);
      }
    );
  }

  deleteRequest(index: number): void {
    const requestId = this.requests[index].id;
    this.currencyService.deleteRequest(requestId).subscribe(
      () => {
        this.requests.splice(index, 1);
      },
      error => {
        console.error('Error deleting request', error);
      }
    );
  }

  
  
}
