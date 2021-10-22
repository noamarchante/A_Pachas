import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MUser} from "./entities/MUser";
import {MUserUser} from "./entities/MUserUser";
import {MUserGroupUser} from "./entities/MUserGroupUser";
import {UserGroupService} from "./userGroup.service";
import {MUserGroup} from "./entities/MUserGroup";

@Injectable({
    providedIn: 'root'
})
export class UserGroupUserService {

    constructor(private http: HttpClient,
                private userGroupService: UserGroupService) { }

    getPageableUsersByUserGroupId(userGroupId: number, page:number, size: number): Observable<MUser[]>{
        return this.http.get<MUser[]>(`${environment.restApi}/usersGroupsUsers/pageable/${userGroupId}?page=${page}&size=${size}`);
    }

    getUsersByUserGroupId(userGroupId: number): Observable<MUser[]>{
        return this.http.get<MUser[]>(`${environment.restApi}/usersGroupsUsers/pageable/${userGroupId}`);
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

    editUserGroupUser(userGroupId: any, userId: number): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersGroupsUsers`,{
            "userGroupId":userGroupId,
            "userId": userId,
            "userGroupUserExited": ""
        })
            .pipe(
                APachasError.throwOnError('Fallo al editar integrantes al grupo', `Por favor, inténtelo de nuevo`)
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
}
