import { Component, OnDestroy } from '@angular/core';
import { CurrencyService } from '../core/currency.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-currency',
  templateUrl: './currency.component.html',
  styleUrls: ['./currency.component.css']
})
export class CurrencyComponent implements OnDestroy{
  currency !: string;
  name !: string;
  value: number | null = null;
  error: string | null = null;

  constructor(private currencyService: CurrencyService) { }

  private subscriptions: Subscription[] = [];

  getCurrencyValue() {

    //to odpowiada za podanie realnie wartości oczekiwanej w dużych literach do poniżej wykonywanej metody
    const upperCaseCurrency = this.currency.toUpperCase();

   const sub = this.currencyService.getCurrentCurrencyValue(upperCaseCurrency, this.name).subscribe(
      response => {
        this.value = response.value;
        this.error = null;
      },
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      error => {
        this.error = 'Invalid currency code or API error';
        this.value = null;
      },
      
    );
    this.subscriptions.push(sub);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe);
  }
}

