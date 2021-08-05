import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MUser} from "./entities/MUser";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(`${environment.restApi}/users`);
  }

  getPageable(userLogin: string, page: number, size: number): Observable<MUser[]>{
    return this.http.get<MUser[]>(`${environment.restApi}/users/pageable/${userLogin}?page=${page}&size=${size}`);
  }

  getPageableUser(userLogin: string, authLogin: string, page: number, size: number): Observable<MUser[]>{
    return this.http.get<MUser[]>(`${environment.restApi}/users/pageableUser/${userLogin}/${authLogin}?page=${page}&size=${size}`);
  }

  get(id: number): Observable<User> {
    return this.http.get<User>(`${environment.restApi}/users/${id}`);
  }

  count(authLogin: string): Observable<number> {
    return this.http.get<number>(`${environment.restApi}/users/count/${authLogin}`);
  }

  searchCount(userLogin: string,authLogin: string): Observable<number> {
    return this.http.get<number>(`${environment.restApi}/users/searchCount/${userLogin}/${authLogin}`);
  }

  getByLogin(login: string): Observable<MUser>{
    return this.http.get<MUser>(`${environment.restApi}/users/byLogin/${login}`);
  }

  create(user: User): Observable<void> {

    return this.http.post<void>(`${environment.restApi}/users`,{
      "userName": user.name,
      "userSurname": user.surname,
      "userLogin": user.login,
      "userPassword": user.password,
      "userEmail": user.email,
      "userBirthday": "",
      "userPhoto": null,
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

  getImage(userId: number): Observable<any> {
    return this.http.get<any>(`${environment.restApi}/users/image/${userId}`);
  }
}