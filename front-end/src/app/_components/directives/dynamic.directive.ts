import {Directive, ViewContainerRef} from '@angular/core';

@Directive({
  selector: '[appModalHolder]'
})
export class DynamicDirective {

  constructor(public viewContainerRef: ViewContainerRef) { }

}
