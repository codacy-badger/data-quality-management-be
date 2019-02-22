import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {DbConfigurationService} from '../../../_services/db-configuration.service';
import {DbConfiguration} from '../../../_models';
import {Subscription} from 'rxjs';
import {AddConfigurationModalComponent} from '../add-configuration-modal/add-configuration-modal.component';
import {BsModalService} from 'ngx-bootstrap';

@Component({
  selector: 'app-db-configuration-list',
  templateUrl: './db-configuration-list.component.html',
  styleUrls: ['./db-configuration-list.component.scss']
})
export class DbConfigurationListComponent implements OnInit, OnDestroy {
  selectedConfigId: number;
  configurations: Array<DbConfiguration> = [];
  configurationsSubscription: Subscription;
  constructor(private dbService: DbConfigurationService, private modalService: BsModalService) { }

  ngOnInit() {
    this.configurationsSubscription = this.dbService.currentConfiguration.subscribe((data: DbConfiguration[]) => {
      this.configurations = data;
    });
  }

  selected(i: number) {
    if (i === this.selectedConfigId) {
      return 'active';
    }
    return '';
  }

  onSelect(i: number) {
    this.selectedConfigId = i;
    this.dbService.selectConfiguration(i).subscribe(
      (data: string[]) => this.dbService.tablesChanged.next(data)
    );
  }

  ngOnDestroy(): void {
    this.configurationsSubscription.unsubscribe();
  }

  addConfig() {
    this.modalService.show(AddConfigurationModalComponent);
  }
}
