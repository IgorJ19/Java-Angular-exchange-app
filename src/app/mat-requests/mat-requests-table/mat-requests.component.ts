import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CurrencyService } from 'src/app/core/currency.service';
import { RequestsComponent } from 'src/app/requestModule/requests/requests.component';

@Component({
  selector: 'app-clients-table',
  templateUrl: './mat-requests.component.html',
  styleUrls: ['./mat-requests.component.css'],
})
export class ClientsTableComponent implements OnInit, AfterViewInit {
  
  displayedColumns: string[] = [
    'Nazwa',
    'Waluta',
    'Data',
    'Wartosc',
  ];

  dataSource: MatTableDataSource<RequestsComponent>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;

  constructor(private clientsService: CurrencyService) {
    this.dataSource = new MatTableDataSource<RequestsComponent>();
  }

  ngOnInit(): void {
    this.clientsService.getRequests().subscribe({
      next: (requests) => {
        this.dataSource.data = requests;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;

        // Dostosowanie sortowania
        this.dataSource.sortingDataAccessor = (item, property) => {
          switch (property) {
            case 'Nazwa': return item.name.toLowerCase();
            case 'Waluta': return item.currency.toLowerCase();
            case 'Data': return new Date(item.requestDate).getTime();  // Konwersja na liczbę
            case 'Wartosc': return item.wartosc;
            default: return '';
          }
        };
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
