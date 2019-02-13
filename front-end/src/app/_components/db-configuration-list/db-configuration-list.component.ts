import { Component, OnInit } from '@angular/core';
import {DbConfigurationService} from '../../_services/db-configuration.service';
import {DbConfiguration} from '../../_models';

@Component({
  selector: 'app-db-configuration-list',
  templateUrl: './db-configuration-list.component.html',
  styleUrls: ['./db-configuration-list.component.scss']
})
export class DbConfigurationListComponent implements OnInit {
  configurations: Array<DbConfiguration> = [];
  constructor(private dbService: DbConfigurationService) { }

  ngOnInit() {
    this.configurations = this.dbService.getConfigurations();
  }

}
