import {Injectable} from '@angular/core';
import {DbConfiguration} from '../_models';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {throws} from 'assert';

@Injectable({
  providedIn: 'root'
})
export class DbConfigurationService {
  configurationsSubject: BehaviorSubject<DbConfiguration[]>;
  currentConfiguration: Observable<DbConfiguration[]>;
  configurations: DbConfiguration[] = [];
  selectedConfiguration = new Subject<DbConfiguration>();
  selectedTable = new Subject<string>();
  tablesChanged = new Subject<string[]>();

  constructor(private http: HttpClient) {
    this.configurationsSubject = new BehaviorSubject<DbConfiguration[]>([]);
    this.currentConfiguration = this.configurationsSubject.asObservable();
  }

  public get currentConfigurationValue(): DbConfiguration[] {
    return this.configurationsSubject.value;
  }

  getAll() {
    this.http.get(`${environment.apiUrl}/db/all`).subscribe(
      (data: DbConfiguration[]) => {
        this.configurations = [...data];
        this.configurationsSubject.next(data);
      });
  }

  testConnection(db: DbConfiguration) {
    return this.http.post(`${environment.apiUrl}/db/test`, db);
  }

  selectConfiguration(i: number) {
    this.selectedConfiguration.next(this.configurations.find(x => x.id === i));
    return this.http.get(`${environment.apiUrl}/db/${i}/tables`);
  }

  selectTable(table: string) {
    this.selectedTable.next(table);
  }

  add(db: DbConfiguration) {
    return this.http.post(`${environment.apiUrl}/db/setup-ds`, db);
  }

  addToList(db: DbConfiguration) {
    this.configurations.splice(0, 0, db);
    this.notify();
  }

  notify() {
    this.configurationsSubject.next(this.configurations.slice());
  }
}
