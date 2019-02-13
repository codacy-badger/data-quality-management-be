import {Component, ComponentFactoryResolver, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {DbConfigurationService} from '../../_services/db-configuration.service';
import {DbConfiguration} from '../../_models';
import {DynamicDirective} from '../../_components/directives/dynamic.directive';
import {ModalComponent} from '../../_components/modal/modal.component';
import {QualityApproachService} from '../../_services/quality-approach.service';
import {QualityApproach} from '../../_models/quality-approach';

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  approaches: Array<QualityApproach> = [];
  constructor(private dbService: DbConfigurationService, private approachService: QualityApproachService) {
  }

  ngOnInit(): void {
  }

  /*isConfigurationsExist(): boolean {
    return this.dbService.getConfigurations().length > 0;
  }*/
}
