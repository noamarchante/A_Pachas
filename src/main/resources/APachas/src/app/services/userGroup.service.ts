import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MUserGroup} from "./entities/MUserGroup";
import {Format} from "@angular-devkit/build-angular/src/extract-i18n/schema";
import {Timestamp} from "rxjs/internal-compatibility";
import {HelperFunction} from "@angular/core/schematics/migrations/renderer-to-renderer2/helpers";

@Injectable({
    providedIn: 'root'
})
export class UserGroupService {

    constructor(private http: HttpClient) { }

    /*getUserGroup(userGroupId: number): Observable<MUserGroup> {
        return this.http.get<MUserGroup>(`${environment.restApi}/usersGroups/${userGroupId}`);
    }*/

    getPageableUserGroupByGroupName(userGroupName: string, authId: number, page: number, size: number): Observable<MUserGroup[]>{
        return this.http.get<MUserGroup[]>(`${environment.restApi}/usersGroups/pageable/${authId}/${userGroupName}?page=${page}&size=${size}`);
    }

    countSearchUsersGroups(userGroupName: string, authId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersGroups/count/${authId}/${userGroupName}`);
    }

    countUsersGroups(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersGroups/count/${authId}`);
    }

    getPageableUserGroup(userGroupId: number, page: number, size: number): Observable<MUserGroup[]> {
        return this.http.get<MUserGroup[]>(`${environment.restApi}/usersGroups/pageable/${userGroupId}?page=${page}&size=${size}`);
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

    editUserGroup(userGroup: MUserGroup): Observable<number> {
        return this.http.put<number>(`${environment.restApi}/usersGroups`,{
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

    deleteUserGroup(userGroup: MUserGroup): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersGroups`, {
            "userGroupId":userGroup.userGroupId,
            "userGroupName": userGroup.userGroupName,
            "userGroupDescription": userGroup.userGroupDescription,
            "userGroupPhoto": userGroup.userGroupPhoto,
            "userGroupRemoval": "",
            "userGroupOwner": userGroup.userGroupOwner
        }).pipe(
            APachasError.throwOnError('Fallo al eliminar grupo', `Por favor, inténtelo de nuevo`)
        );
    }
}
