import {Component, OnInit, ViewChild} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap';
import {NgForm} from '@angular/forms';
import {DbConfiguration} from '../../../_models';
import {DbConfigurationService} from '../../../_services/db-configuration.service';

@Component({
  selector: 'app-add-configuration-modal',
  templateUrl: './add-configuration-modal.component.html',
  styleUrls: ['./add-configuration-modal.component.scss']
})
export class AddConfigurationModalComponent implements OnInit {
  @ViewChild('form') addConfigurationForm: NgForm;
  connectionTested = false;
  testLoading = false;
  saveLoading = false;

  constructor(public bsModalRef: BsModalRef, private dbService: DbConfigurationService) {}

  ngOnInit() {
  }

  onSubmit() {
    const config = this.addConfigurationForm.value as DbConfiguration;
    if (!this.addConfigurationForm.valid) {
      console.log(config);
      return;
    }
    this.saveLoading = true;
    this.dbService.add(config).subscribe(
      (db: DbConfiguration) => {
        this.dbService.addToList(db);
        this.saveLoading = false;
        this.bsModalRef.hide();
      },
      error => {
        this.saveLoading = false;
        throw error;
      }
      );
  }

  testConnection() {
    const config = this.addConfigurationForm.value as DbConfiguration;
    this.testLoading = true;
    this.dbService.testConnection(config).subscribe(
      () => {
        this.connectionTested = true;
        this.testLoading = false;
      },
      error => {
        this.connectionTested = false;
        this.testLoading = false;
        throw error;
      }
    );
  }
}
