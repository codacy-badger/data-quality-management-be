export class DbConfiguration {
  constructor(
    public id: number,
    public type: number,
    public databaseName: string,
    public username: string,
    public password: string,
    public host: string,
    public port: number ) {}
}
