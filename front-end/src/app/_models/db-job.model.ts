import {QualityApproach} from './quality-approach';

export class DbJobModel {
  constructor(
    public dbConfigurationId?: number,
    public tableName?: string,
    public approaches?: QualityApproach[]
  ) {}
}
