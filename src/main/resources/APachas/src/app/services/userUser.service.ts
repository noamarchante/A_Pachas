import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {User} from "./entities/User";
import {MUser} from "../models/MUser";
import {UserUser} from "./entities/UserUser";
import {MUserUser} from "../models/MUserUser";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class UserUserService {

    constructor(private http: HttpClient) { }

    getUserUser(friendId: number, userId: number): Observable<MUserUser> {
        return this.http.get<UserUser>(`${environment.restApi}/usersUsers/${friendId}/${userId}`);
    }

    getFriends(friendId: number): Observable<MUser[]> {
        return this.http.get<User[]>(`${environment.restApi}/usersUsers/${friendId}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    createUserUser(userUser: MUserUser): Observable<void> {
        return this.http.post<void>(`${environment.restApi}/usersUsers`,{
            "friendId":userUser.friendId,
            "userId": userUser.userId,
            "status": userUser.status
        })
            .pipe(
                APachasError.throwOnError('Fallo en la solicitud de amistad o solicitud ya enviada', `Por favor, inténtelo de nuevo`)
            );
    }

    editUserUser(userUser: MUserUser): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersUsers`,{
            "friendId":userUser.friendId,
            "userId": userUser.userId,
            "status": userUser.status
        })
            .pipe(
                APachasError.throwOnError('Fallo al aceptar la solicitud', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
            );
    }

    deleteUserUser(friendId: number, userId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/usersUsers/${friendId}/${userId}`);
    }

    private mapUser(user: User) : MUser {
        return {
            userId: user.userId,
            userName: user.userName,
            userSurname: user.userSurname,
            userLogin: user.userLogin,
            userPassword: user.userPassword,
            userEmail: user.userEmail,
            userBirthday: new Date(user.userBirthday),
            userPhoto: user.userPhoto,
            roles: user.roles,
            permissions: user.permissions
        }
    }
}
