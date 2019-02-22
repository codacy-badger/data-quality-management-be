import { Injectable } from '@angular/core';
import {QualityApproach} from '../_models/quality-approach';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QualityApproachService {
  approaches: Array<QualityApproach> = [];
  approachesChanged = new Subject<QualityApproach[]>();
  constructor() {
    this.approaches.push(
      new QualityApproach('notNull', true),
      new QualityApproach('NLP'),
    );
  }
  getApproaches(): Array<QualityApproach> {
    return this.approaches;
  }

  setApproches(approaches: QualityApproach[]) {
    this.approaches = [...approaches];
    this.approachesChanged.next(this.approaches.slice());
  }
}
