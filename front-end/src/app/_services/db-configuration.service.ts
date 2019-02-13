import { Injectable } from '@angular/core';
import {DbConfiguration} from '../_models';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DbConfigurationService {
  private configurations: Array<DbConfiguration> = [];
  constructor(private http: HttpClient) {
    console.log('DbConfigurationService');
  }

  getConfigurations(): Array<DbConfiguration> {
    console.log('DbConfigurationService');
    this.http.get(`${environment.apiUrl}/db/config/all`).subscribe(data => {
      this.configurations.push(...(data as Array<DbConfiguration>));
    });
    return [...this.configurations];
  }
}
