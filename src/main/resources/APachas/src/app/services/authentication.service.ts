import { Injectable } from '@angular/core';
import {HttpClient, HttpRequest} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../models/User';
import {APachasError} from '../modules/notification/entities';

//SERVICE -> Se encarga de acceder a los datos para entregarlos a los componentes
@Injectable({
  providedIn: 'root'
})

//ACCEDE A LOS DATOS NECESARIOS PARA LA AUTENTICACION
export class AuthenticationService {

	private user: User = new User();
	authorizationHeader: string;

	constructor(private  http: HttpClient) {}

  //COMPRUEBA SI LAS CREDENCIALES SON CORRECTAS
	checkCredentials(login: string, password: string) {

		this.user.login = login;
		this.user.password = password;

    return this.http.post<void>(`${environment.restApi}/login`, {
      "username": this.user.login,
      "password": this.user.password
    }, {observe:"response" as "body", responseType: 'json'})
      .pipe(
        APachasError.throwOnError('Failed to login', `User or password incorrect. Please try again.`)
      )/*.subscribe((data: any) => {
          this.authorizationHeader = data.headers.get('authorization');
          console.log("Authorization " + this.authorizationHeader);
        })*/;
	}

  //CONFIGURA AL USUARIO LOGGEADO
	public logIn(login: string, password: string, authorization: string ) {
    this.authorizationHeader = authorization;
    console.log(this.authorizationHeader);
	  this.user.login = login;
		this.user.password = password;
		this.user.authHeader = this.authorizationHeader;
		this.user.authenticated = true;

		this.user.save();
	}

	//CIERRE DE SESION
	public logOut() {
		this.user.clear();
		this.user = new User();
	}

	//CABECERA DE LA AUTORIZACION => TOKEN
	public getAuthorizationHeader(): string {
		return this.authorizationHeader;
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
