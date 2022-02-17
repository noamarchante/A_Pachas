import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {UserUserEvent} from "./entities/UserUserEvent";
import {MUserUserEvent} from "../models/MUserUserEvent";
import {APachasError} from "../modules/notification/entities";

@Injectable({
    providedIn: 'root'
})
export class UserUserEventService {

    constructor(private http: HttpClient) { }

    createUserUserEvent(eventId: any): Observable<void> {
        return this.http.post<void>(`${environment.restApi}/usersUsersEvents`, eventId)
            .pipe(
                APachasError.throwOnError('Fallo al añadir transacciones', `Por favor, inténtelo de nuevo`)
            );
    }

    getPageableUserUserEvents(eventId: number, page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/${eventId}/?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    countUserUserEvents(eventId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/${eventId}`);
    }

    getPageableSearchUserUserEvents(transactionActorName: string, eventId: number, page: number, size: number): Observable<MUserUserEvent[]>{
        return this.http.get<UserUserEvent[]>(`${environment.restApi}/usersUsersEvents/pageable/${transactionActorName}/${eventId}/?page=${page}&size=${size}`).pipe(
            map(userUserEvents => userUserEvents.map(this.mapUserUserEvent.bind(this)))
        );
    }

    countSearchUserUserEvents(transactionActorName: string, eventId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersUsersEvents/count/${transactionActorName}/${eventId}`);
    }

    private mapUserUserEvent(userUserEvent: UserUserEvent) : MUserUserEvent {
        return {
            senderId: userUserEvent.senderId,
            receiverId: userUserEvent.receiverId,
            eventId: userUserEvent.eventId,
            confirmed: userUserEvent.confirmed,
            cost: userUserEvent.cost,
            paid: userUserEvent.paid,
            userUserEventActive: userUserEvent.userUserEventActive,
            userUserEventRemoval: userUserEvent.userUserEventRemoval,
            userUserEventCreation: userUserEvent.userUserEventCreation
        }
    }
}
