/* eslint-disable @typescript-eslint/no-explicit-any */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RequestsComponent } from '../requestModule/requests/requests.component';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private apiUrl = 'http://localhost:8080/currencies';

  //wstrzyknięte zależności z HttpClient zaimplementowanego w app.module, dzięki czemu możemy używać np this.http.post
  constructor(private http: HttpClient) { }

  //metoda z dwoma argumentami typu Observable co pozwala na subskrybowanie się, wykorzystujemy dane z konstruktora i wskazujemy wykonanie postmappingu z serwera
  //w {currency, name} przeekazujemy body, czyli to co chcemy wysłać do serwera, tak działa metoda post z httpClient 
  getCurrentCurrencyValue(currency: string, name: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/get-current-currency-value-command`, { currency, name });
  }

  //pobieramy tablicę wszystkich elementów zawartych w bazie danych
  getRequests(): Observable<RequestsComponent[]> {
    return this.http.get<RequestsComponent[]>(`${this.apiUrl}/requests`);
  }

  //przekazujemy delete endpoint 
  deleteRequest(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/requests/${id}`);
    
  }

  patchRequest(id: number, name: string): Observable<any> {
    const body = { name: name }; 
    return this.http.patch(`${this.apiUrl}/requests/patch/${id}`, body);
}

}
