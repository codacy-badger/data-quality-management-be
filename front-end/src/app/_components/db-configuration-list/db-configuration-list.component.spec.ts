import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DbConfigurationListComponent } from './db-configuration-list.component';

describe('DbConfigurationListComponent', () => {
  let component: DbConfigurationListComponent;
  let fixture: ComponentFixture<DbConfigurationListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DbConfigurationListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DbConfigurationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
