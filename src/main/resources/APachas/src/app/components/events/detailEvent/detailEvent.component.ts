import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from "../../../services/user.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {NotificationService} from "../../../modules/notification/services/notification.service";
import {MUser} from "../../../models/MUser";
import {MEvent} from "../../../models/MEvent";
import {EventService} from "../../../services/event.service";
import {UserEventService} from "../../../services/userEvent.service";
import {STATUS} from "../../users/listUsers/listUsers.component";

@Component({
    selector: 'app-detailEvent',
    templateUrl: './detailEvent.component.html',
    styleUrls: ['./detailEvent.component.css']
})
export class DetailEventComponent implements OnInit {

    @Output()
    eventMessage = new EventEmitter<number>();

    @Output()
    eventDelete = new EventEmitter<boolean>();

    @Output()
    eventDetail = new EventEmitter<number>();

    defaultUserImage: string = "./assets/user.png";
    defaultImage: string = "./assets/event.jpg";

    eventPartakers: MUser[] = [];
    eventPartakersStored: MUser[] = [];
    pagePartaker = 0;
    sizePartaker = 6;
    totalPartaker: number=0;
    more: string = "Ver más ...";
    message: string = "";

    previousEvent: string="";
    nextEvent: string="";

    _previous: boolean = false;
    _next: boolean = false;
    _event: MEvent = new MEvent();
    status:string="";
    messageRequest: boolean;


    private return = 'events';

    constructor(private route: ActivatedRoute,
                private router: Router,
                private eventService: EventService,
                private userService: UserService,
                private userEventService: UserEventService,
                private authenticationService: AuthenticationService,
                private notificationService: NotificationService
    ) {
    }

    ngOnInit() {
        this.paginationEventClass();
    }

    messageValue(request: boolean) {
        this.messageRequest = request;
        if(request){
            if (this.status == STATUS.PENDING){
                this.message = "¿Quieres aceptar la solicitud para participar en el evento?";
            }else{
                this.message = "¿Estás seguro de que quieres salir del evento?";
            }
        }else if (!request) {
            if (this.authenticationService.getUser().id == this.event.eventOwner) {
                this.message = "¿Estás seguro de que deseas eliminar el evento?";
            }
        }
    }

    get previous(){
        return this._previous;
    }

    @Input() set previous( previous: boolean){
        this._previous = previous;
    }

    get next(){
        return this._next;
    }

    @Input() set next( next: boolean){
        this._next = next;
    }


    get event(){
        return this._event;
    }

    @Input() set event (event: MEvent) {
        if (event != undefined) {
            this._event = event;
            if (this.event.eventId != null){
                this.getPartakers(this.event.eventId);
                this.getTotalPartakers(this.event.eventId);
                this.getUserEvent();
            }else{
                this._event = new MEvent();
            }
        }
        this.eventPartakers = [];
        this.eventPartakersStored = [];
    }

    onDelete($event){
        if($event.valueOf()){
            if (this.event.eventOwner == this.authenticationService.getUser().id){
                this.eventService.deleteEvent(this.event.eventId).subscribe(()=>{
                        this.eventDelete.emit();
                        this.notificationService.success("Evento eliminado", "Se ha eliminado el evento correctamente.");

                    }
                );
            }else{
                this.userEventService.deleteUserEvent(this.event.eventId, this.authenticationService.getUser().id).subscribe(() => {
                    this.eventDelete.emit();
                    this.notificationService.success("Eliminado del evento", "Ya no eres un participante de este evento.");
                });
            }
        }
    }

    editDelete(): boolean{
        if (this.event.eventOwner == this.authenticationService.getUser().id){
            return true;
        }else{
            return false;
        }
    }

    ownerLabel(userId:number):string{
        let value:string = "";
        if (userId == this.event.eventOwner){
            value = "Administrador";
        }
        return value;
    }

    ownerBorder(userId: number):string{
        if(userId == this.event.eventOwner){
            return "owner";
        }
    }

    openDetailsEvent(){

    }

    setPageEvent(number: number){
        this.eventDetail.emit(number);
    }

    getPartakers(eventId:number){
        this.userEventService.getPageablePartakers(eventId,this.pagePartaker, this.sizePartaker).subscribe((response) => {
            this.eventPartakers.push(...response);
            this.paginationEventClass();
            this.setMoreLabel();
        });
    }

    getTotalPartakers(eventId:number){
        this.userEventService.countPartakers(eventId).subscribe((partakers)=>{
            this.totalPartaker = partakers;
        });
    }

    setMoreLabel(){
        if (this.eventPartakers.length == this.totalPartaker){
            this.more = "... Ver menos";
        }else{
            this.more = "Ver más ...";
        }
    }

    getMorePartakers(){
        if (this.eventPartakers.length == this.totalPartaker){
            this.eventPartakersStored = this.eventPartakers;
            this.eventPartakers = this.eventPartakers.slice(0,this.sizePartaker);
        }else{
            if (this.eventPartakersStored.length ==0){
                this.pagePartaker +=1;
                this.getPartakers(this.event.eventId);
            }else{
                this.eventPartakers = this.eventPartakersStored;
            }
        }
        this.setMoreLabel();
    }

    private paginationEventClass(){
        if(this._previous && this._next){
            this.previousEvent = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
                this.nextEvent = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
        }else if (!this._previous && this._next){
            this.previousEvent = "";
            this.nextEvent = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
        }else if(this._previous && !this._next){
            this.previousEvent = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.nextEvent = "";
        }else{
            this.previousEvent = "";
            this.nextEvent = "";
        }
    }


    onRequest($event){
        console.log("ON REQUEST MESSAGEREQUEST");
        console.log(this.messageRequest);
        if (this.messageRequest){
            console.log("ON REQUEST MESSAGEREQUEST TRUE");
            console.log($event.valueOf());
            this.setStatus($event);
            this.eventMessage.emit();
        }else if (!this.messageRequest){
            this.onDelete($event);
        }
    }

    setStatus($event){
        console.log("SET STATUS");
        console.log($event.valueOf());
        if ($event.valueOf() && this.status == STATUS.PENDING){
            console.log("set statis edit");
            this.userEventService.editStatus(this.event.eventId, this.authenticationService.getUser().id).subscribe();
        }else{
            this.userEventService.deleteUserEvent(this.event.eventId, this.authenticationService.getUser().id).subscribe();
        }
    }

    getStatus(status: boolean){
        if (status){
            this.status = STATUS.FOLLOW;
        }else{
            this.status = STATUS.PENDING;
        }
    }

    getUserEvent(){
        this.userEventService.getUserEvent(this.event.eventId, this.authenticationService.getUser().id).subscribe((response) =>{
            this.getStatus(response.accept);
        });

    }
}