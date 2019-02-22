import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { JobRoutingModule } from './job-routing.module';
import {JobComponent} from './job.component';
import {DbConfigurationListComponent} from '../../_components';
import { TablesListComponent } from './tables-list/tables-list.component';
import {AddConfigurationModalComponent} from './add-configuration-modal/add-configuration-modal.component';
import { ApproachListComponent } from './approach-list/approach-list.component';

@NgModule({
  declarations: [
    DbConfigurationListComponent,
    JobComponent,
    TablesListComponent,
    ApproachListComponent,
  ],
  imports: [
    CommonModule,
    JobRoutingModule
  ]
})
export class JobModule { }
