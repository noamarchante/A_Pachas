import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {EventService} from "../../../services/event.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {UserGroupService} from "../../../services/userGroup.service";
import {STATUS} from "../listUsers/listUsers.component";
import {UserUserService} from "../../../services/userUser.service";
import {MUser} from "../../../models/MUser";
import {MUserGroup} from "../../../models/MUserGroup";
import {MUserUser} from "../../../models/MUserUser";
import {MEvent} from "../../../models/MEvent";


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
    defaultGroupImage: string = "./assets/group.jpg";
    defaultImage: string = "./assets/user.png";
    previousUser: string;
    nextUser: string;

    mutualGroups: MUserGroup[] = [];
    mutualGroupsStored: MUserGroup[] = [];
    pageMutualGroups = 0;
    sizeMutualGroups = 6;
    totalMutualGroups: number=0;
    moreGroups: string = "Ver más ...";

    mutualFriends: MUser[] = [];
    mutualFriendsStored: MUser[] = [];
    pageMutualFriends = 0;
    sizeMutualFriends = 6;
    totalMutualFriends: number=0;
    moreFriends: string = "Ver más ...";

    mutualEvents: MEvent[] = [];
    mutualEventsStored: MEvent[] = [];
    pageMutualEvents = 0;
    sizeMutualEvents = 6;
    totalMutualEvents: number=0;
    moreEvents: string = "Ver más ...";

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
                private userGroupService: UserGroupService
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
            if (this.user.userId != null){
                this.getMutualUserGroups(this.user.userId);
                this.getTotalMutualFriends(this.user.userId);
                this.getTotalMutualGroups(this.user.userId);
                this.getTotalMutualEvents(this.user.userId);
            }else{
                this._user = new MUser();
            }
        }
        this.mutualGroups = [];
        this.mutualFriends = [];
        this.mutualEvents = [];
        this.mutualGroupsStored = [];
        this.mutualFriendsStored = [];
        this.mutualEventsStored = [];
    }


    get previous(){
        return this._previous;
    }

    @Input() set previous( previous: boolean){
        this._previous = previous;
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
            mUserUser.status = false;
            this.userUserService.createUserUser(mUserUser).subscribe();
        }else if (this.status == STATUS.FOLLOW || this.status == STATUS.SENT) {
            this.deleteUserUser();
        }else if (this.status == STATUS.PENDING){
            let mUserUser: MUserUser = new MUserUser();
            mUserUser.userId = this.user.userId;
            mUserUser.friendId = this.authenticationService.getUser().id;
            mUserUser.status = true;
            this.userUserService.editUserUser(mUserUser).subscribe();
        }
    }

    private deleteUserUser(){
        this.userUserService.deleteUserUser(this.user.userId, this.authenticationService.getUser().id).subscribe();
    }

    getMutualUserGroups(userId:number){
        this.userGroupService.getPageableMutualUserGroups(userId, this.authenticationService.getUser().id,this.pageMutualGroups, this.sizeMutualGroups).subscribe((response) => {
            this.mutualGroups.push(...response);
            this.paginationUserClass();
            this.setMoreMutualGroupsLabel();
        });
    }

    getTotalMutualGroups(userId:number){
        this.userGroupService.countMutualGroups(userId, this.authenticationService.getUser().id).subscribe((number)=>{
            this.totalMutualGroups = number;
        });
    }

    setMoreMutualGroupsLabel(){
        if (this.mutualGroups.length == this.totalMutualGroups){
            this.moreGroups = "... Ver menos";
        }else{
            this.moreGroups = "Ver más ...";
        }
    }

    getMoreMutualGroups(){
        if (this.mutualGroups.length == this.totalMutualGroups){
            this.mutualGroupsStored = this.mutualGroups;
            this.mutualGroups = this.mutualGroups.slice(0,this.sizeMutualGroups);
        }else{
            if (this.mutualGroupsStored.length ==0){
                this.pageMutualGroups +=1;
                this.getMutualUserGroups(this.user.userId);
            }else{
                this.mutualGroups = this.mutualGroupsStored;
            }
        }
        this.setMoreMutualGroupsLabel();
    }



    getMutualFriends(userId:number){
        this.userService.getPageableMutualFriends(userId, this.authenticationService.getUser().id,this.pageMutualFriends, this.sizeMutualFriends).subscribe((response) => {
            this.mutualFriends.push(...response);
            this.paginationUserClass();
            this.setMoreMutualFriendsLabel();
        });
    }

    getTotalMutualFriends(userId:number){
        this.userService.countMutualFriends(userId, this.authenticationService.getUser().id).subscribe((number)=>{
            this.totalMutualFriends = number;
        });
    }

    setMoreMutualFriendsLabel(){
        if (this.mutualFriends.length == this.totalMutualFriends){
            this.moreFriends = "... Ver menos";
        }else{
            this.moreFriends = "Ver más ...";
        }
    }

    getMoreMutualFriends(){
        if (this.mutualFriends.length == this.totalMutualFriends){
            this.mutualFriendsStored = this.mutualFriends;
            this.mutualFriends = this.mutualFriends.slice(0,this.sizeMutualFriends);
        }else{
            if (this.mutualFriendsStored.length ==0){
                this.pageMutualFriends +=1;
                this.getMutualFriends(this.user.userId);
            }else{
                this.mutualFriends = this.mutualFriendsStored;
            }
        }
        this.setMoreMutualFriendsLabel();
    }

    getMutualEvents(userId:number){
        this.eventService.getPageableMutualEvents(userId, this.authenticationService.getUser().id,this.pageMutualEvents, this.sizeMutualEvents).subscribe((response) => {
            this.mutualEvents.push(...response);
            this.paginationUserClass();
            this.setMoreMutualEventsLabel();
        });
    }

    getTotalMutualEvents(userId:number){
        this.eventService.countMutualEvents(userId, this.authenticationService.getUser().id).subscribe((number)=>{
            this.totalMutualEvents = number;
        });
    }

    setMoreMutualEventsLabel(){
        if (this.mutualEvents.length == this.totalMutualEvents){
            this.moreEvents = "... Ver menos";
        }else{
            this.moreEvents = "Ver más ...";
        }
    }

    getMoreMutualEvents(){
        if (this.mutualEvents.length == this.totalMutualEvents){
            this.mutualEventsStored = this.mutualEvents;
            this.mutualEvents = this.mutualEvents.slice(0,this.sizeMutualEvents);
        }else{
            if (this.mutualEventsStored.length ==0){
                this.pageMutualEvents +=1;
                this.getMutualEvents(this.user.userId);
            }else{
                this.mutualEvents = this.mutualEventsStored;
            }
        }
        this.setMoreMutualEventsLabel();
    }
}

