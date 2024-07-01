import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'currency-exchange-app'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('currency-exchange-app');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.content span')?.textContent).toContain('currency-exchange-app app is running!');
  });
});

/* describe grupuje związane ze sobą testy i umożliwia ich organizację, beforeEach(jak forEach) wykonuje się przed 
  każdym testem zdefiniowanym w describe. RouterTestingModule symuluje routing dla elementów które go wykorzystują.
  declarations wskazuje na element który ma zostać poddany testom. CompileComponents kompiluje komponenty i ich szablony asynchronicznie.

  should create the app to próba odpalenia aplikacji, create coponent symuluje postawienie elementu AppComponent. Potem do app 
  przekazujemy "wyssaną " instancję appComponent i sprawdzamy czy zwracana jest inna wartość niż null lub undefined.

  następny sprawdza czy tytuł zgadza się ze wskazanym 

  w ostatnim po pobraniu stanu AppComponent wykrywane są zmiany, pobierany jest natywny element DOM przedstawiający szablon komponentu
  i sprawdzany jest zwracany komunikat
   
*/