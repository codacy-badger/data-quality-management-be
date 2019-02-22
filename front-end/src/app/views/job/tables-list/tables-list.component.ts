import {Component, OnDestroy, OnInit} from '@angular/core';
import {DbConfigurationService} from '../../../_services/db-configuration.service';
import {Subscription} from 'rxjs';
import index from '@angular/cli/lib/cli';

@Component({
  selector: 'app-tables-list',
  templateUrl: './tables-list.component.html',
  styleUrls: ['./tables-list.component.scss']
})
export class TablesListComponent implements OnInit, OnDestroy {
  tables: string[] = [];
  tablesSubscription: Subscription;
  selectedTableIndex: number;
  loading = false;

  constructor(private dbService: DbConfigurationService) {
  }

  ngOnInit() {
    this.tablesSubscription = this.dbService.tablesChanged.subscribe(data => this.tables = [...data]);

  }

  selected(tableIndex: number) {
    if (tableIndex === this.selectedTableIndex) {
      return 'active';
    }
    return '';
  }

  onSelect(tableIndex: number) {
    this.selectedTableIndex = tableIndex;
    this.dbService.selectTable(this.tables[tableIndex]);
  }

  ngOnDestroy(): void {
    this.tablesSubscription.unsubscribe();
  }

}
