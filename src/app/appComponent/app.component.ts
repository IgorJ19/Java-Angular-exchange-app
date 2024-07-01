import { Component } from '@angular/core';

// Standardowy plik każdego komponentu odpowiadającyte za wskazanie: selectora (np. do wyświetlenia w głównym HTMLU  
//   templateUrl wskazuje ścieżkę do pliku dostosowującego wygląd strony podobnie jak styleUrls)
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'currency-exchange-app';
}
