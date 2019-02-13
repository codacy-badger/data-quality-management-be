import { Injectable } from '@angular/core';
import {QualityApproach} from '../_models/quality-approach';

@Injectable({
  providedIn: 'root'
})
export class QualityApproachService {
  approaches: Array<QualityApproach> = [];
  constructor() {
    this.approaches.push(
      new QualityApproach('notNull', true),
      new QualityApproach('NLP'),
    );
  }
  getApproaches(): Array<QualityApproach> {
    return this.approaches;
  }
}
