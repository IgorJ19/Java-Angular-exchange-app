import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Data } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private apiUrl = 'http://localhost:8080/currencies';

  constructor(private http: HttpClient) { }

  getCurrentCurrencyValue(currency: string, name: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/get-current-currency-value-command`, { currency, name });
  }

  getRequests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/requests`);
  }

  deleteRequest(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/requests/${id}`);
    
  }
}
