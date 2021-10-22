 import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../models/User';
import {APachasError} from '../modules/notification/entities';
import {UserService} from "./user.service";

//SERVICE -> Se encarga de acceder a los datos para entregarlos a los componentes
@Injectable({
  providedIn: 'root'
})

//ACCEDE A LOS DATOS NECESARIOS PARA LA AUTENTICACION
export class AuthenticationService {

	private user: User = new User();

	constructor(private  http: HttpClient,  private userService: UserService) {}

  //COMPRUEBA SI LAS CREDENCIALES SON CORRECTAS
	checkCredentials(login: string, password: string) {

		this.user.login = login;
		this.user.password = password;

		return this.http.post<void>(`${environment.restApi}/login`, {
		  "username": this.user.login,
		  "password": this.user.password
		}, {observe:"response" as "body", responseType: 'json'}
		)
		  .pipe(
			APachasError.throwOnError('Failed to login', `User or password incorrect. Please try again.`)
		  );
	}

  //CONFIGURA AL USUARIO LOGGEADO
	public logIn(login: string, password: string, authorization: string) {
	  this.user.login = login;
		this.user.password = password;
		this.user.authHeader = authorization;
		this.user.authenticated = true;
		this.setAuthUser(login);

	}

	//RECOGER OTROS DATOS DEL USUARIO AUTENTICADO
	public setAuthUser(login: string){
		this.userService.getUserByLogin(login).subscribe((response) => {
			this.user.id = response.userId;
			this.user.email = response.userEmail;
			this.user.permission = response.permissions;
			this.user.rol = response.roles;
			this.user.save();
		});

	}

	//CIERRE DE SESION
	public logOut() {
		this.user.clear();
		this.user = new User();
	}

	//CABECERA DE LA AUTORIZACION => TOKEN
	public getAuthorizationHeader(): string {
		return this.getUser().authHeader;
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
