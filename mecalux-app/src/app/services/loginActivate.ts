import { LoginService } from './login.service';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class LoginActivate implements CanActivate {
  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean>|Promise<boolean>|boolean {
    if (!this.loginService.isLoggedIn()) {
      this.router.navigate(['login']);
    }
    return true;
  }
}
