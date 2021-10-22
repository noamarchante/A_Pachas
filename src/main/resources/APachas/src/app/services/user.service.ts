import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MUser} from "./entities/MUser";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPageableUser(authId: number, page: number, size: number): Observable<MUser[]>{
    return this.http.get<MUser[]>(`${environment.restApi}/users/pageable/${authId}?page=${page}&size=${size}`);
  }

  getPageableUserByLogin(userLogin: string, authId: number, page: number, size: number): Observable<MUser[]>{
    return this.http.get<MUser[]>(`${environment.restApi}/users/pageable/${userLogin}/${authId}?page=${page}&size=${size}`);
  }

  countUsers(authId: number): Observable<number> {
    return this.http.get<number>(`${environment.restApi}/users/count/${authId}`);
  }

  countSearchUsers(userLogin: string, authId: number): Observable<number> {
    return this.http.get<number>(`${environment.restApi}/users/count/${userLogin}/${authId}`);
  }

  getUserByLogin(login: string): Observable<MUser>{
    return this.http.get<MUser>(`${environment.restApi}/users/${login}`);
  }

  createUser(mUser: MUser): Observable<void> {

    return this.http.post<void>(`${environment.restApi}/users`,{
      "userName": mUser.userName,
      "userSurname": mUser.userSurname,
      "userLogin": mUser.userLogin,
      "userPassword": mUser.userPassword,
      "userEmail": mUser.userEmail,
      "userBirthday": "",
      "userPhoto": null,
      "roles": "USER",
      "permissions": ""
    })
      .pipe(
        APachasError.throwOnError('Fallo en el registro', `Los datos del formulario son incorrectos o el usuario ya existe. Por favor, inténtelo de nuevo`)
      );
  }

  /*update(user: User): Observable<User> {
    return this.http.put<User>(`${environment.restApi}/users`, user);
  }

  get(id: number): Observable<User> {
    return this.http.get<User>(`${environment.restApi}/users/${id}`);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.restApi}/users/${id}`);
  }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(`${environment.restApi}/users`);
  }

  getImage(userId: number): Observable<any> {
    return this.http.get<any>(`${environment.restApi}/users/image/${userId}`);
  }*/
}
