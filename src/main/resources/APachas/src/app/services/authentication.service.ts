import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../models/User';
import {Observable} from 'rxjs';
import {APachasError} from '../modules/notification/entities';

//SERVICE -> Se encarga de acceder a los datos para entregarlos a los componentes
@Injectable({
  providedIn: 'root'
})

//ACCEDE A LOS DATOS NECESARIOS PARA LA AUTENTICACION
export class AuthenticationService {

	private user: User = new User();

	constructor(private  http: HttpClient) {}

  //COMPRUEBA SI LAS CREDENCIALES SON CORRECTAS
	checkCredentials(login: string, password: string): Observable<void> {

		this.user.login = login;
		this.user.password = password;
    console.log("AUTHENTICATION.SERVICE" + login);
    console.log("AUTHENTICATION.SERVICE" + password);

    console.log("AUTHENTICATION.SERVICE USER" + this.user.login);
    console.log("AUTHENTICATION.SERVICE USER" + this.user.password);
  console.log("URL " + `${environment.restApi}/login`);
    return this.http.post<void>(`${environment.restApi}/login`, {
      "username":this.user.login,
      "password": this.user.password
    })
      .pipe(
        APachasError.throwOnError('Failed to login', `User or password incorrect. Please try again.`)
      );

	}

  //CONFIGURA AL USUARIO LOGGEADO
	public logIn(login: string, password: string) {
		this.user.login = login;
		this.user.password = password;
    console.log("AUTHENTICATION.SERVICE LOGIN" + login);
    console.log("AUTHENTICATION.SERVICE LOGIN" + password);
		this.user.authHeader = this.getAuthorizationHeader();
		this.user.authenticated = true;
		this.user.save();
	}

	//CIERRE DE SESION
	public logOut() {
		this.user.clear();
		this.user = new User();
	}

	//CABECERA DE LA AUTORIZACION => TOKEN
  //como consigo el token de la cabecera de la respuesta del login
	public getAuthorizationHeader(): string {
		return 'Basic ' + btoa(this.user.login + ':' + this.user.password);
	}

	//DEVUELVE EL USUARIO AUTENTICADO
	public getUser(): User {
		return this.user;
	}

	//COMPRUEBA SI ES UN INVITADO
	public isGuest(): boolean {
		return !this.user.authenticated;
	}
}
