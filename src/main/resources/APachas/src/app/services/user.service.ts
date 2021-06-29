import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(`${environment.restApi}/users`);
  }

  getPageable(): Observable<User[]>{
    return this.http.get<User[]>(`${environment.restApi}/users/pageable?page=0&size=5`);
  }

  get(id: number): Observable<User> {
    return this.http.get<User>(`${environment.restApi}/users/${id}`);
  }

  create(user: User): Observable<void> {

    return this.http.post<void>(`${environment.restApi}/users`,{
      "userName": user.name,
      "userSurname": user.surname,
      "userLogin": user.login,
      "userPassword": user.password,
      "userEmail": user.email,
      "userBirthday": "",
      "userPhoto": "",
      "roles": "USER",
      "permissions": ""
    })
      .pipe(
        APachasError.throwOnError('Fallo en el registro', `Los datos del formulario son incorrectos o el usuario ya existe. Por favor, inténtelo de nuevo`)
      );
  }

  update(user: User): Observable<User> {
    return this.http.put<User>(`${environment.restApi}/users`, user);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.restApi}/users/${id}`);
  }
}
