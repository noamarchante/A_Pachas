import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {MEvent} from "../models/MEvent";
import {Event} from "./entities/Event";
import {map} from "rxjs/operators";



@Injectable({
    providedIn: 'root'
})
export class EventService {

    constructor(private http: HttpClient) { }

    countMutualEvents(userId: number, authId: number): Observable<number>{
        return this.http.get<number>(`${environment.restApi}/events/countMutual/${userId}/${authId}`);
    }

    getPageableMutualEvents(userId: number, authId: number, page: number, size: number): Observable<MEvent[]>{
        return this.http.get<Event[]>(`${environment.restApi}/events/pageableMutual/${userId}/${authId}?page=${page}&size=${size}`).pipe(
            map(events => events.map(this.mapEvent.bind(this)))
        );
    }

    private mapEvent(event: Event) : MEvent {
        return {
                eventId: event.eventId,
                eventName:event.eventName,
                eventDescription:event.eventDescription,
                eventStartDate: new Date(event.eventStartDate),
                eventEndDate: new Date(event.eventEndDate),
                eventLocation:event.eventLocation,
                eventPhoto:event.eventPhoto,
                eventState:event.eventState,
                userId:event.userId,
                eventFinalPrice:event.eventFinalPrice
        }
    }

}
