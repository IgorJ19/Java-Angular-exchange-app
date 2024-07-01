import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { CurrencyComponent } from './currency/currency.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';



const routes: Routes = [
  { path: '', redirectTo: '/currency', pathMatch: 'full' },
  { path: 'currency', component: CurrencyComponent },
  // lazy loading
  { path: 'requests', loadChildren: ()  => import('./requestModule/request.module').then (m => m.RequestModule) },
  { path: 'mat-requests', loadChildren: () => import('./mat-requests/clients.module').then (m => m.ClientsModule)},
  { path: '**', component: PageNotFoundComponent},
];

//preloadingStratego: PreloadModules pozwala na ładowanie się w pierwszej kolejności tych elementów które mają EagerLoading
// ale kiedy odpalimy np. currency component to w tle już będą się nam pobierały najpierw eager a potem lazy 
@NgModule({
  imports: [RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
