import {Component, OnDestroy, Inject, OnInit} from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { navItems } from './../../_nav';
import {AuthenticationService} from '../../_services';


@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent implements OnInit, OnDestroy {
  public navItems = navItems;
  public sidebarMinimized = true;
  private changes: MutationObserver;
  public element: HTMLElement;
  avatarPath =  'simple';
  currentDate = new Date();
  constructor(private authService: AuthenticationService, @Inject(DOCUMENT) _document?: any) {

    this.changes = new MutationObserver((mutations) => {
      this.sidebarMinimized = _document.body.classList.contains('sidebar-minimized');
    });
    this.element = _document.body;
    this.changes.observe(<Element>this.element, {
      attributes: true,
      attributeFilter: ['class']
    });

  }

  ngOnInit(): void {
    if (this.authService.isManager()) {
      this.avatarPath = 'manager';
    } else if (this.authService.isSuper()) {
      this.avatarPath = 'super';
    } else if (this.authService.isSimple()) {
      this.avatarPath = 'simple';
    }
  }

  ngOnDestroy(): void {
    this.changes.disconnect();
  }
}
