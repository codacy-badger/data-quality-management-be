import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {JwtHelperService} from '@auth0/angular-jwt';
import {environment} from '../../environments/environment';
import {User} from '../_models';
import {TokenStorageService} from './token-storage.service';

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;
  private jwt: string;
  private user: User;

  constructor(private http: HttpClient, private tokenService: TokenStorageService) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${environment.apiUrl}/login`, {username, password}, {observe: 'response'});
  }

  saveToken(jwt: string) {
    this.tokenService.saveToken(jwt);
    this.jwt = jwt;
    this.parseJWT();

  }

  parseJWT() {
    const jwtHelper = new JwtHelperService();
    if (!jwtHelper.isTokenExpired(this.jwt)) {
      const jwtObject = jwtHelper.decodeToken(this.jwt);
      this.setUser(jwtObject.sub, this.jwt, jwtObject.roles);
      this.currentUserSubject.next(this.getUser());
    }
  }

  logout() {
    // remove user from local storage to log user out
    this.tokenService.signOut();
    this.currentUserSubject.next(null);
  }

  loadToken() {
    this.jwt = this.tokenService.getToken();
    if (this.jwt) {
      this.parseJWT();
    }
  }

  isManager() {
    return this.user.roles.indexOf('MANAGER') > -1;
  }

  isSuper() {
    return this.user.roles.indexOf('SUPER_USER') > -1;
  }

  isSimple() {
    return this.user.roles.indexOf('SIMPLE_USER') > -1;
  }

  isAuthenticated() {
    return this.user.roles && (this.isManager() || this.isSuper() || this.isSimple());
  }

  private setUser(username: string, token: string, roles: Array<string>) {
    this.user = new User(username, token, roles);
  }

  private getUser(): User {
    return this.user;
  }
}
