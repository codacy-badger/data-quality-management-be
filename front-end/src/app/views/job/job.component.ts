import {Component, OnDestroy, OnInit} from '@angular/core';
import {QualityApproach} from '../../_models/quality-approach';
import {DbConfigurationService} from '../../_services/db-configuration.service';
import {QualityApproachService} from '../../_services/quality-approach.service';
import {Subscription} from 'rxjs';
import {BsModalService} from 'ngx-bootstrap';
import {AddConfigurationModalComponent} from './add-configuration-modal/add-configuration-modal.component';
import {DbJobModel} from '../../_models/db-job.model';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.scss']
})
export class JobComponent implements OnInit, OnDestroy {
  approaches: Array<QualityApproach> = [];
  configurationsExists = false;
  configurationsSubscription: Subscription;
  loading = false;


  constructor(
    private dbService: DbConfigurationService,
    private approachService: QualityApproachService,
    private modalService: BsModalService
  ) {
  }

  ngOnInit(): void {
    this.approaches = [...this.approachService.getApproaches()];
    this.dbService.getAll();
    this.configurationsSubscription = this.dbService.currentConfiguration.subscribe(data => {
      this.configurationsExists = data != null && data.length > 0;
    });
    if (!this.configurationsExists) {
      this.modalService.show(AddConfigurationModalComponent);
    }
  }

  isConfigurationsExist(): boolean {
    return this.configurationsExists;
  }

  ngOnDestroy(): void {
    this.configurationsSubscription.unsubscribe();
  }
}
