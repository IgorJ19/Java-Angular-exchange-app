import { Component } from '@angular/core';
import { CurrencyService } from '../currency.service';

@Component({
  selector: 'app-currency',
  templateUrl: './currency.component.html',
  styleUrls: ['./currency.component.css']
})
export class CurrencyComponent {
  currency = '';
  name = '';
  value: number | null = null;
  error: string | null = null;

  constructor(private currencyService: CurrencyService) { }

  getCurrencyValue() {
    if (!this.name) {
      this.error = 'You need to specify the name';
      return;
    }

    const upperCaseCurrency = this.currency.toUpperCase();

    this.currencyService.getCurrentCurrencyValue(upperCaseCurrency, this.name).subscribe(
      response => {
        this.value = response.value;
        this.error = null;
      },
      error => {
        this.error = 'Invalid currency code or API error';
        this.value = null;
      },
      
    );
  }
}

