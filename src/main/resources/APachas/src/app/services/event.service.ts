import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {MEvent} from "../models/MEvent";
import {APachasError} from "../modules/notification/entities";

@Injectable({
    providedIn: 'root'
})
export class EventService {

    constructor(private http: HttpClient) { }



    createEvent(mEvent: MEvent): Observable<number> {
        return this.http.post<number>(`${environment.restApi}/events`,{
            "eventId": mEvent.eventId,
            "eventName":mEvent.eventName,
            "eventDescription":mEvent.eventDescription,
            "eventStart": mEvent.eventStart,
            "eventEnd": mEvent.eventEnd,
            "eventLocation":mEvent.eventLocation,
            "eventPhoto":mEvent.eventPhoto,
            "eventOpen": "",
            "eventOwner":mEvent.eventOwner,
            "eventActive": "",
            "eventCreation": "",
            "eventRemoval": ""
        })
            .pipe(
                APachasError.throwOnError('Fallo al crear el evento', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
            );
    }

    editEvent(mEvent: MEvent): Observable<void> {
        return this.http.put<void>(`${environment.restApi}/events`,{
            "eventId": mEvent.eventId,
            "eventName":mEvent.eventName,
            "eventDescription":mEvent.eventDescription,
            "eventStart": mEvent.eventStart,
            "eventEnd": mEvent.eventEnd,
            "eventLocation":mEvent.eventLocation,
            "eventPhoto":mEvent.eventPhoto,
            "eventOpen":mEvent.eventOpen,
            "eventOwner":mEvent.eventOwner,
            "eventActive": mEvent.eventActive,
            "eventCreation": "",
            "eventRemoval": ""
        })
            .pipe(
                APachasError.throwOnError('Fallo al editar el evento', `Por favor, compruebe que los datos son correcto e inténtelo de nuevo`)
            );
    }

    editOpen(eventId: number, open: boolean): Observable<void>{
        return this.http.put<void>(`${environment.restApi}/events/open/${eventId}`, open)
            .pipe(
                APachasError.throwOnError('Fallo al cerrar evento', `Por favor, inténtelo de nuevo`)
            );
    }

    deleteEvent(eventId: number): Observable<void> {
        return this.http.delete<void>(`${environment.restApi}/events/${eventId}`).pipe(
            APachasError.throwOnError('Fallo al eliminar evento', `Por favor, inténtelo de nuevo`)
        );
    }
}