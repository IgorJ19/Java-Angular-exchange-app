import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { RequestsComponent } from './requests/requests.component';
import { ModalComponent } from '../modal/modal.component';
import { FormsModule } from '@angular/forms';

const routes: Routes = [
  { path: '', component: RequestsComponent }
];

// Aby moduł ładowany z opóźnieniem działał prawidłowo, musi 
// zawierać własne trasy zdefiniowane w RouterModule.forChild(routes)
// W module ładowanym z opóźnieniem używamy RouterModule.forChild(routes), 
// aby zarejestrować pod-trasy specyficzne dla tego modułu.

@NgModule({
  declarations: [RequestsComponent, ModalComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    

  ],
})
export class RequestModule { }
