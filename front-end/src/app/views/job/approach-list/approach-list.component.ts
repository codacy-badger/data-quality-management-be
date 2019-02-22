import {Component, OnDestroy, OnInit} from '@angular/core';
import {QualityApproach} from '../../../_models/quality-approach';
import {QualityApproachService} from '../../../_services/quality-approach.service';
import {Subscription} from 'rxjs';
import {DbConfigurationService} from '../../../_services/db-configuration.service';
import {DbJobModel} from '../../../_models/db-job.model';

@Component({
  selector: 'app-approach-list',
  templateUrl: './approach-list.component.html',
  styleUrls: ['./approach-list.component.scss']
})
export class ApproachListComponent implements OnInit, OnDestroy {
  loading = false;
  approaches: Array<QualityApproach> = [];
  approachSubscription: Subscription;
  dbSubscription: Subscription;
  tableSubscription: Subscription;
  job: DbJobModel = new DbJobModel();

  constructor(private approachService: QualityApproachService, private dbService: DbConfigurationService) {
  }

  ngOnInit() {
    this.approaches = this.approachService.getApproaches();
    this.job.approaches = this.approachService.getApproaches();
    this.approachSubscription = this.approachService.approachesChanged.subscribe(data => {
      this.approaches = [...data];
      this.job.approaches = [...data];
    });
    this.dbSubscription = this.dbService.selectedConfiguration.subscribe(data => {
      this.job.dbConfigurationId = data.id;
    });
    this.tableSubscription = this.dbService.selectedTable.subscribe(data => {
      this.job.tableName = data;
    });
  }

  changeState(approach: string) {
    const app = this.approaches.find(a => a.name === approach);
    app.activated = !app.activated;
    this.approachService.setApproches(this.approaches);
  }

  onSchuduleIt() {
    if (!this.validJob()) {
      return;
    }
    console.log(this.job);
  }

  ngOnDestroy(): void {
    this.approachSubscription.unsubscribe();
  }

  validJob(): boolean {
    return this.job.tableName != null && this.job.dbConfigurationId > 0 && this.job.approaches.filter(x => x.activated).length > 0;
  }

}
