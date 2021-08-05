import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";
import {APachasError} from "../modules/notification/entities";
import {MUserUser} from "./entities/MUserUser";

@Injectable({
    providedIn: 'root'
})
export class UserUserService {

    constructor(private http: HttpClient) { }

    getAll(): Observable<MUserUser[]> {
        return this.http.get<MUserUser[]>(`${environment.restApi}/users`);
    }

    getPageable(page: number, size: number): Observable<MUserUser[]>{
        return this.http.get<MUserUser[]>(`${environment.restApi}/users/pageable?page=${page}&size=${size}`);
    }

    count(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsers/count/${authId}`);
    }

    getFriendshipRequest(authId: number): Observable<MUserUser> {
        return this.http.get<MUserUser>(`${environment.restApi}/usersUsers/${authId}`);
    }



   /* getPageableUser(userLogin: string, page: number, size: number): Observable<MUserUser[]>{
        return this.http.get<MUserUser[]>(`${environment.restApi}/users/pageableUser/${userLogin}?page=${page}&size=${size}`);
    }*/

    get(friendId: number, userId: number): Observable<MUserUser> {
        return this.http.get<MUserUser>(`${environment.restApi}/usersUsers/${friendId}/${userId}`);
    }

    create(userUser: MUserUser): Observable<void> {
        return this.http.post<void>(`${environment.restApi}/usersUsers`,{
            "friendId":userUser.friendId,
            "userId": userUser.userId,
            "status": userUser.status
        })
            .pipe(
                APachasError.throwOnError('Fallo en la solicitud de amistad o solicitud ya enviada', `Por favor, inténtelo de nuevo`)
            );
    }

    update(user: MUserUser): Observable<MUserUser> {
        return this.http.put<MUserUser>(`${environment.restApi}/usersUsers`, user);
    }

    delete( friendId: number, userId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/usersUsers/${friendId}/${userId}`);
    }
}
