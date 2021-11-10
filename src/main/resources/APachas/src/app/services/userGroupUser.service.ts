import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MUser} from "../models/MUser";
import {User} from "./entities/User";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class UserGroupUserService {

    constructor(private http: HttpClient) { }

    getPageableUsersByUserGroupId(userGroupId: number, page:number, size: number): Observable<MUser[]>{
        return this.http.get<User[]>(`${environment.restApi}/usersGroupsUsers/pageable/${userGroupId}?page=${page}&size=${size}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    getUsersByUserGroupId(userGroupId: number): Observable<MUser[]>{
        return this.http.get<User[]>(`${environment.restApi}/usersGroupsUsers/pageable/${userGroupId}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    countUsersGroupsUsersByUserGroupId(userGroupId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersGroupsUsers/count/${userGroupId}`);
    }
    createUserGroupUser(userGroupId: any, userId: number): Observable<void> {
        return this.http.post<void>(`${environment.restApi}/usersGroupsUsers`,{
            "userGroupId":userGroupId,
            "userId": userId,
            "userGroupUserExited": ""
        })
            .pipe(
                APachasError.throwOnError('Fallo al añadir integrantes al grupo', `Por favor, inténtelo de nuevo`)
            );
    }

    editUserGroupUser(userGroupId: number,userIds: number[]): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersGroupsUsers/${userGroupId}`,userIds)
            .pipe(
                APachasError.throwOnError('Fallo al editar integrantes del grupo', `Por favor, inténtelo de nuevo`)
            );
    }

    deleteUserGroupUser(userGroupId: number, userId: number): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersGroupsUsers`, {
            "userGroupId":userGroupId,
            "userId": userId,
            "userGroupUserExited": ""
        }).pipe(
            APachasError.throwOnError('Fallo al eliminar usuario del grupo', `Por favor, inténtelo de nuevo`)
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
            permissions: user.permissions
        }
    }
}
