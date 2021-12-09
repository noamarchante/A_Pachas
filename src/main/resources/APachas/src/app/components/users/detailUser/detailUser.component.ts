import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {EventService} from "../../../services/event.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {GroupService} from "../../../services/group.service";
import {STATUS} from "../listUsers/listUsers.component";
import {UserUserService} from "../../../services/userUser.service";
import {MUser} from "../../../models/MUser";
import {MGroup} from "../../../models/MGroup";
import {MUserUser} from "../../../models/MUserUser";
import {MEvent} from "../../../models/MEvent";
import {GroupUserService} from "../../../services/groupUser.service";
import {UserEventService} from "../../../services/userEvent.service";


export enum MESSAGE{
    CANCELREQUEST = '¿Cancelar solicitud?', UNFOLLOW = '¿Dejar de seguir?', ALLOWREQUEST = '¿Aceptar solicitud?', SENTREQUEST = '¿Enviar solicitud?'
}

@Component({
    selector: 'app-detailUser',
    templateUrl: './detailUser.component.html',
    styleUrls: ['./detailUser.component.css']
})

export class DetailUserComponent implements OnInit {

    @Output()
    eventMessage = new EventEmitter<number>();
    defaultEventImage: string = "./assets/event.jpg";
    defaultGroupImage: string = "./assets/group.jpg";
    defaultImage: string = "./assets/user.png";
    previousUser: string;
    nextUser: string;

    mutualGroups: MGroup[] = [];
    mutualGroupsStored: MGroup[] = [];
    pageMutualGroups = 0;
    sizeMutualGroups = 2;
    totalMutualGroups: number=0;

    mutualFriends: MUser[] = [];
    mutualFriendsStored: MUser[] = [];
    pageMutualFriends = 0;
    sizeMutualFriends = 4;
    totalMutualFriends: number=0;

    mutualEvents: MEvent[] = [];
    mutualEventsStored: MEvent[] = [];
    pageMutualEvents = 0;
    sizeMutualEvents = 4;
    totalMutualEvents: number=0;

    message:string="";
    _status:string="";
    _previous: boolean = false;
    _next: boolean = false;
    _user: MUser = new MUser();

    @Output()
    eventDetail = new EventEmitter<number>();

    constructor(private route: ActivatedRoute,
                private router: Router,
                private userService: UserService,
                private authenticationService: AuthenticationService,
                private userUserService: UserUserService,
                private eventService: EventService,
                private userEventService: UserEventService,
                private groupService: GroupService,
                private groupUserService: GroupUserService
    ) {
    }

    ngOnInit() {
        this.paginationUserClass();
    }

    get user(){
        return this._user;
    }

    @Input() set user (user: MUser) {
        if (user != undefined) {
            this._user = user;
            this.mutualReset();
            if (this.user.userId != null){
                this.getMutualUserGroups(this.user.userId);
                this.getMutualFriends(this.user.userId);
                this.getMutualEvents(this.user.userId);

                this.getTotalMutualFriends(this.user.userId);
                this.getTotalMutualGroups(this.user.userId);
                this.getTotalMutualEvents(this.user.userId);
            }else{
                this._user = new MUser();
            }
        }
        this.paginationUserClass();
    }

    mutualReset(){
        this.mutualGroups = [];
        this.mutualFriends = [];
        this.mutualEvents = [];

        this.mutualGroupsStored = [];
        this.mutualFriendsStored = [];
        this.mutualEventsStored = [];

        this.pageMutualGroups = 0;
        this.pageMutualFriends = 0;
        this.pageMutualEvents = 0;
    }


    get previous(){
        return this._previous;
    }

    @Input() set previous( previous: boolean){
        this._previous = previous;
        this.paginationUserClass();

    }

    get status(){
        return this._status;
    }

    @Input() set status( status: string){
        this._status = status;
        this.setMessage();
    }

    get next(){
        return this._next;
    }

    @Input() set next( next: boolean){
        this._next = next;
        this.paginationUserClass();

    }

    setPage(number: number){
        this.eventDetail.emit(number);
    }

    setMessage(){
        if (this.status == STATUS.FOLLOW){
            this.message = MESSAGE.UNFOLLOW;
        }else if (this.status == STATUS.SENT){
            this.message = MESSAGE.CANCELREQUEST;
        }else if (this.status == STATUS.PENDING){
            this.message = MESSAGE.ALLOWREQUEST;
        }else if (this.status == STATUS.REQUEST){
            this.message = MESSAGE.SENTREQUEST;
        }
    }

    private paginationUserClass(){
        if(this._previous && this._next){
            this.previousUser = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
            this.nextUser = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
        }else if (!this._previous && this._next){
            this.previousUser = "";
            this.nextUser = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
        }else if(this._previous && !this._next){
            this.previousUser = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.nextUser = "";
        }else{
            this.previousUser = "";
            this.nextUser = "";
        }
    }

    onRequest($event){
        if ($event){
            this.setStatus();
            this.eventMessage.emit();
        }else if (!$event && this.status == STATUS.PENDING){
            this.deleteUserUser();
            this.eventMessage.emit();
        }
    }

    setStatus(){
        if (this.status == STATUS.REQUEST){
            let mUserUser: MUserUser = new MUserUser();
            mUserUser.userId = this.authenticationService.getUser().id;
            mUserUser.friendId = this.user.userId;
            this.userUserService.getDeletedUserUser(this.user.userId,this.authenticationService.getUser().id).subscribe((response)=>{
                if (response != null && !response.userUserActive){
                    mUserUser.userUserActive = true;
                    mUserUser.accept = false;
                    this.userUserService.editUserUser(mUserUser).subscribe();
                }else{
                    this.userUserService.createUserUser(mUserUser).subscribe();
                }
            });
        }else if (this.status == STATUS.FOLLOW || this.status == STATUS.SENT) {
            this.deleteUserUser();
        }else if (this.status == STATUS.PENDING){
            let mUserUser: MUserUser = new MUserUser();
            mUserUser.userId = this.user.userId;
            mUserUser.friendId = this.authenticationService.getUser().id;
            mUserUser.accept = true;
            this.userUserService.editUserUser(mUserUser).subscribe();
        }
    }

    private deleteUserUser(){
        this.userUserService.deleteUserUser(this.user.userId, this.authenticationService.getUser().id).subscribe();
    }

    getMutualUserGroups(userId:number){
        this.groupUserService.getPageableMutualGroups(userId, this.authenticationService.getUser().id,this.pageMutualGroups, this.sizeMutualGroups).subscribe((response) => {
            this.mutualGroups.push(...response);
        });
    }

    getTotalMutualGroups(userId:number){
        this.groupUserService.countMutualGroups(userId, this.authenticationService.getUser().id).subscribe((number)=>{
            this.totalMutualGroups = number;
        });
    }

    getMoreMutualGroups(){
        this.pageMutualGroups +=1;
        if (this.mutualGroups.length < this.mutualGroupsStored.length){
            this.mutualGroups = this.mutualGroupsStored.slice(0,this.sizeMutualGroups*(this.pageMutualGroups+1));
        }else{
            this.getMutualUserGroups(this.user.userId);
        }
    }

    getLessMutualGroups(){
        if (this.mutualGroupsStored.length != this.totalMutualGroups){
            this.mutualGroupsStored = this.mutualGroups;
            this.mutualGroups = this.mutualGroups.slice(0,this.sizeMutualGroups*this.pageMutualGroups);
        }else{
            this.mutualGroups = this.mutualGroupsStored.slice(0, this.sizeMutualGroups*this.pageMutualGroups);
        }
        this.pageMutualGroups -=1;
    }

    getMutualFriends(userId:number){
        this.userUserService.getPageableMutualFriends(userId, this.authenticationService.getUser().id,this.pageMutualFriends, this.sizeMutualFriends).subscribe((response) => {
            this.mutualFriends.push(...response);
        });
    }

    getTotalMutualFriends(userId:number){
        this.userUserService.countMutualFriends(userId, this.authenticationService.getUser().id).subscribe((number)=>{
            this.totalMutualFriends = number;
        });
    }

    getMoreMutualFriends(){
        this.pageMutualFriends +=1;
        if (this.mutualFriends.length < this.mutualFriendsStored.length){
            this.mutualFriends = this.mutualFriendsStored.slice(0,this.sizeMutualFriends*(this.pageMutualFriends+1));
        }else{
            this.getMutualFriends(this.user.userId);
        }
    }

    getLessMutualFriends(){
        if (this.mutualFriendsStored.length != this.totalMutualFriends){
            this.mutualFriendsStored = this.mutualFriends;
            this.mutualFriends = this.mutualFriends.slice(0,this.sizeMutualFriends*this.pageMutualFriends);
        }else{
            this.mutualFriends = this.mutualFriendsStored.slice(0, this.sizeMutualFriends*this.pageMutualFriends);
        }
        this.pageMutualFriends -=1;
    }

    getMutualEvents(userId:number){
        this.userEventService.getPageableMutualEvents(userId, this.authenticationService.getUser().id,this.pageMutualEvents, this.sizeMutualEvents).subscribe((response) => {
            this.mutualEvents.push(...response);
        });
    }

    getTotalMutualEvents(userId:number){
        this.userEventService.countMutualEvents(userId, this.authenticationService.getUser().id).subscribe((number)=>{
            console.log(number);
            this.totalMutualEvents = number;
        });
    }

    getMoreMutualEvents(){
        this.pageMutualEvents +=1;
        if (this.mutualEvents.length < this.mutualEventsStored.length){
            this.mutualEvents = this.mutualEventsStored.slice(0,this.sizeMutualEvents*(this.pageMutualEvents+1));
        }else{
            this.getMutualEvents(this.user.userId);
        }
    }

    getLessMutualEvents(){
        if (this.mutualEventsStored.length != this.totalMutualEvents){
            this.mutualEventsStored = this.mutualEvents;
            this.mutualEvents = this.mutualEvents.slice(0,this.sizeMutualEvents*this.pageMutualEvents);
        }else{
            this.mutualEvents = this.mutualEventsStored.slice(0, this.sizeMutualEvents*this.pageMutualEvents);
        }
        this.pageMutualEvents -=1;
    }
}

