import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MUserGroup} from "../models/MUserGroup";
import {UserGroup} from "./entities/UserGroup";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class UserGroupService {

    constructor(private http: HttpClient) { }


    getPageableUserGroupByGroupName(userGroupName: string, authId: number, page: number, size: number): Observable<MUserGroup[]>{
        return this.http.get<UserGroup[]>(`${environment.restApi}/usersGroups/pageable/${authId}/${userGroupName}?page=${page}&size=${size}`).pipe(
            map(userGroups => userGroups.map(this.mapUserGroup.bind(this)))
        );
    }

    countSearchUsersGroups(userGroupName: string, authId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersGroups/count/${authId}/${userGroupName}`);
    }

    countUsersGroups(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersGroups/count/${authId}`);
    }

    getPageableUserGroup(userGroupId: number, page: number, size: number): Observable<MUserGroup[]> {
        return this.http.get<UserGroup[]>(`${environment.restApi}/usersGroups/pageable/${userGroupId}?page=${page}&size=${size}`).pipe(
            map(userGroups => userGroups.map(this.mapUserGroup.bind(this)))
        );
    }

    createUserGroup(userGroup: MUserGroup): Observable<number> {
        return this.http.post<number>(`${environment.restApi}/usersGroups`,{
            "userGroupId":userGroup.userGroupId,
            "userGroupName": userGroup.userGroupName,
            "userGroupDescription": userGroup.userGroupDescription,
            "userGroupPhoto": userGroup.userGroupPhoto,
            "userGroupRemoval": "",
            "userGroupOwner": userGroup.userGroupOwner
        })
            .pipe(
                APachasError.throwOnError('Fallo al crear el grupo', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
            );
    }

    editUserGroup(userGroup: MUserGroup): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersGroups`,{
            "userGroupId":userGroup.userGroupId,
            "userGroupName": userGroup.userGroupName,
            "userGroupDescription": userGroup.userGroupDescription,
            "userGroupPhoto": userGroup.userGroupPhoto,
            "userGroupRemoval": "",
            "userGroupOwner": userGroup.userGroupOwner
        })
            .pipe(
                APachasError.throwOnError('Fallo al editar el grupo', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
            );
    }

    deleteUserGroup(mUserGroup: MUserGroup): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersGroups/delete`, {
            "userGroupId":mUserGroup.userGroupId,
            "userGroupName": mUserGroup.userGroupName,
            "userGroupDescription": mUserGroup.userGroupDescription,
            "userGroupPhoto": mUserGroup.userGroupPhoto,
            "userGroupRemoval": "",
            "userGroupOwner": mUserGroup.userGroupOwner
        }).pipe(
            APachasError.throwOnError('Fallo al eliminar grupo', `Por favor, inténtelo de nuevo`)
        );
    }

    countMutualGroups(userId: number, authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersGroups/countMutual/${userId}/${authId}`);
    }

    getPageableMutualUserGroups(userId: number, authId: number, page: number, size: number): Observable<MUserGroup[]>{
        return this.http.get<UserGroup[]>(`${environment.restApi}/usersGroups/pageableMutual/${userId}/${authId}?page=${page}&size=${size}`).pipe(
            map(userGroups => userGroups.map(this.mapUserGroup.bind(this)))
        );
    }

    private mapUserGroup(userGroup: UserGroup) : MUserGroup {
        return {
            userGroupId: userGroup.userGroupId,
            userGroupName: userGroup.userGroupName,
            userGroupDescription: userGroup.userGroupDescription,
            userGroupPhoto: userGroup.userGroupPhoto,
            userGroupCreation: userGroup.userGroupCreation,
            userGroupRemoval: userGroup.userGroupRemoval,
            userGroupOwner: userGroup.userGroupOwner
        }
    }
}
