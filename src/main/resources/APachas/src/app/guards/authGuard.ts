import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
/*
Es un servicio injectable que devuelve true si el usuario puede acceder a una ruta o false si no puede.
 */
@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
	constructor(private router: Router) {
	}

	canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
	if (localStorage.getItem('currentUser')) {
		return true;
	}
	this.router.navigate(['/login']);

	return false;
	}
}
