import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {User} from "./entities/User";
import {map} from "rxjs/operators";
import {MUser} from "../models/MUser";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPageableUsers(authId: number, page: number, size: number): Observable<MUser[]>{
    return this.http.get<User[]>(`${environment.restApi}/users/pageable/${authId}?page=${page}&size=${size}`).pipe(
        map(users => users.map(this.mapUser.bind(this)))
    );
  }

  getPageableSearchUsers(userLogin: string, authId: number, page: number, size: number): Observable<MUser[]>{
    return this.http.get<User[]>(`${environment.restApi}/users/pageable/${userLogin}/${authId}?page=${page}&size=${size}`).pipe(
        map(users => users.map(this.mapUser.bind(this)))
    );
  }

  countUsers(authId: number): Observable<number> {
    return this.http.get<number>(`${environment.restApi}/users/count/${authId}`);
  }

  countSearchUsers(userLogin: string, authId: number): Observable<number> {
    return this.http.get<number>(`${environment.restApi}/users/count/${userLogin}/${authId}`);
  }

  getUser(userLogin: string): Observable<MUser>{
    return this.http.get<User>(`${environment.restApi}/users/${userLogin}`).pipe(
        map(this.mapUser.bind(this))
    );
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
      "permissions": "",
      "userCreation": "",
      "userRemoval": "",
      "userActive": true
    })
      .pipe(
        APachasError.throwOnError('Fallo en el registro', `Los datos del formulario son incorrectos o el usuario ya existe. Por favor, inténtelo de nuevo`)
      );
  }



  private mapUser(user: User) : MUser {
    return {
      userId: user.userId,
      userName: user.userName,
      userSurname: user.userSurname,
      userLogin: user.userLogin,
      userPassword: user.userPassword,
      userEmail: user.userEmail,
      userBirthday: user.userBirthday,
      userPhoto: user.userPhoto,
      roles: user.roles,
      permissions: user.permissions,
      userActive: user.userActive,
      userRemoval: user.userRemoval,
      userCreation: user.userCreation
    }
  }

}
