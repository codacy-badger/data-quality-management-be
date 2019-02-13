import { Component, OnInit } from '@angular/core';
import {QualityApproach} from '../../_models/quality-approach';
import {DbConfigurationService} from '../../_services/db-configuration.service';
import {QualityApproachService} from '../../_services/quality-approach.service';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.scss']
})
export class JobComponent implements OnInit {
  approaches: Array<QualityApproach> = [];
  loading = false;
  constructor(private dbService: DbConfigurationService, private approachService: QualityApproachService) {
  }

  ngOnInit(): void {
    this.approaches = [...this.approachService.getApproaches()];
  }

  changeState(approach: string) {
    const app = this.approaches.find( a => a.name === approach);
    app.activated = !app.activated;
  }
  onSchuduleIt() {
    this.loading = true;
  }
}
