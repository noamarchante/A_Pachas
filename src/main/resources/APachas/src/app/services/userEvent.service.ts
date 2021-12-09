import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {MEvent} from "../models/MEvent";
import {Event} from "./entities/Event";
import {map} from "rxjs/operators";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {MUser} from "../models/MUser";
import {User} from "./entities/User";
import {APachasError} from "../modules/notification/entities";
import {UserEvent} from "./entities/UserEvent";
import {MUserEvent} from "../models/MUserEvent";

@Injectable({
    providedIn: 'root'
})
export class UserEventService {

    constructor(private http: HttpClient) {
    }

    countMutualEvents(userId: number, authId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/mutual/${userId}/${authId}`);
    }

    getPageableMutualEvents(userId: number, authId: number, page: number, size: number): Observable<MEvent[]> {
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/pageable/mutual/${userId}/${authId}?page=${page}&size=${size}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    getPageableSearchEvents(eventName: string, authId: number, page: number, size: number): Observable<MEvent[]>{
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/pageable/${eventName}/${authId}/?page=${page}&size=${size}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    countSearchEvents(eventName: string, authId: number): Observable<number> {
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/${eventName}/${authId}`);
    }

    countEvents(authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/${authId}`);
    }

    getPageableEvents(authId: number, page: number, size: number): Observable<MEvent[]> {
        return this.http.get<Event[]>(`${environment.restApi}/usersEvents/pageable/${authId}?page=${page}&size=${size}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    getPageablePartakers(eventId: number, page:number, size: number): Observable<MUser[]>{
        return this.http.get<User[]>(`${environment.restApi}/usersEvents/pageable/partakers/${eventId}?page=${page}&size=${size}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    countPartakers(eventId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/usersEvents/count/partakers/${eventId}`);
    }

    deleteUserEvent(eventId: number, userId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/usersEvents/${eventId}/${userId}`).pipe(
            APachasError.throwOnError('Fallo al eliminar participante del evento', `Por favor, inténtelo de nuevo`)
        );
    }

    createUserEvent(eventId: any, userId: number): Observable<void> {
        console.log(eventId);
        console.log(userId);
        return this.http.post<void>(`${environment.restApi}/usersEvents`,{
            "eventId":eventId,
            "userId": userId,
            "accept": false,
            "totalExpense": 0,
            "userEventActive": true,
            "userEventCreation": "",
            "userEventRemoval":""
        })
            .pipe(
                APachasError.throwOnError('Fallo al añadir participantes al evento', `Por favor, inténtelo de nuevo`)
            );
    }

    editUserEvent(eventId: number, userIds: number[]): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/usersEvents/${eventId}`,userIds)
            .pipe(
                APachasError.throwOnError('Fallo al editar participantes del evento', `Por favor, inténtelo de nuevo`)
            );
    }

    editStatus(eventId: number, authId: number): Observable<void>{
        return this.http.put<void>(`${environment.restApi}/usersEvents/status/${eventId}`, authId)
            .pipe(
                APachasError.throwOnError('Fallo al editar el estado de los participantes del evento', `Por favor, inténtelo de nuevo`)
            );
    }

    getPartakers(eventId: number): Observable<MUser[]>{
        return this.http.get<User[]>(`${environment.restApi}/usersEvents/${eventId}`).pipe(
            map(users => users.map(this.mapUser.bind(this)))
        );
    }

    getUserEvent(eventId: number, userId: number): Observable<MUserEvent>{
        return this.http.get<UserEvent>(`${environment.restApi}/usersEvents/${eventId}/${userId}`).pipe(
            map(this.mapUserEvent.bind(this))
        );
    }

    private mapEvent(event: Event) : MEvent {
        return {
            eventId: event.eventId,
            eventName:event.eventName,
            eventDescription:event.eventDescription,
            eventStart: event.eventStart,
            eventEnd: event.eventEnd,
            eventLocation:event.eventLocation,
            eventPhoto:event.eventPhoto,
            eventOpen:event.eventOpen,
            eventOwner:event.eventOwner,
            eventActive: event.eventActive,
            eventCreation: event.eventCreation,
            eventRemoval: event.eventRemoval
        }
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

    private mapUserEvent(userEvent: UserEvent) : MUserEvent {
        return {
            userEventId: userEvent.userEventId,
            totalExpense: userEvent.totalExpense,
            accept: userEvent.accept,
            userEventActive: userEvent.userEventActive,
            userEventRemoval: userEvent.userEventRemoval,
            userEventCreation: userEvent.userEventCreation
        }
    }
}