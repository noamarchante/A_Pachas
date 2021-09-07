import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MUserGroup} from "./entities/MUserGroup";

@Injectable({
    providedIn: 'root'
})
export class UserGroupUserService {

    constructor(private http: HttpClient) { }

    /*getAll(): Observable<MUserGroup[]> {
        return this.http.get<MUserGroup[]>(`${environment.restApi}/users`);
    }

    getPageable(page: number, size: number): Observable<MUserGroup[]>{
        return this.http.get<MUserGroup[]>(`${environment.restApi}/users/pageable?page=${page}&size=${size}`);
    }

    count(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsers/count/${authId}`);
    }

    getFriendshipRequest(authId: number): Observable<MUserGroup> {
        return this.http.get<MUserGroup>(`${environment.restApi}/usersUsers/${authId}`);
    }
*/


    /* getPageableUser(userLogin: string, page: number, size: number): Observable<MUserUser[]>{
         return this.http.get<MUserUser[]>(`${environment.restApi}/users/pageableUser/${userLogin}?page=${page}&size=${size}`);
     }*/

    get(userGroupId: number): Observable<MUserGroup> {
        return this.http.get<MUserGroup>(`${environment.restApi}/usersGroups/${userGroupId}`);
    }

    create(userGroupId: any, userId: number): Observable<void> {
        return this.http.post<void>(`${environment.restApi}/usersGroupsUsers`,{
            "userGroupId":userGroupId,
            "userId": userId,
            "userGroupUserExited": ""
        })
            .pipe(
                APachasError.throwOnError('Fallo al añadir integrantes al grupo', `Por favor, inténtelo de nuevo`)
            );
    }

    /*update(user: MUserGroup): Observable<MUserGroup> {
        return this.http.put<MUserGroup >(`${environment.restApi}/usersUsers`, user);
    }

    delete( friendId: number, userId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/usersUsers/${friendId}/${userId}`);
    }*/
}
