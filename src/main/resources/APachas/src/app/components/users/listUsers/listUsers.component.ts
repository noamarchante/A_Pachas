import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from "../../../services/user.service";
import {MUser} from "../../../services/entities/MUser";
import {MUserUser} from "../../../services/entities/MUserUser";
import {UserUserService} from "../../../services/userUser.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {DomSanitizer} from "@angular/platform-browser";
import {MUserGroup} from "../../../services/entities/MUserGroup";


export enum STATUS {
    REQUEST = 'Solicitar amistad', PENDING = 'Solicitud pendiente', FOLLOW = 'Siguiendo', SENT = 'Solicitud enviada'
}

export enum MESSAGE{
    CANCELREQUEST = '¿Cancelar solicitud?', UNFOLLOW = '¿Dejar de seguir?', ALLOWREQUEST = '¿Aceptar solicitud?'
}

@Component({
    selector: 'app-users',
    templateUrl: './listUsers.component.html',
    styleUrls: ['./listUsers.component.css']
})
export class ListUsersComponent implements OnInit {
    login = "";
    users: MUser[] = [];
    images: {[index:number]: any;} = {};
    defaultImage = "./assets/user.png";
    messages: {[index: number]: any;} = {};
    friends: {[index: number]: any;} = {};
    totalPage = 0;
    page = 0;
    private size = 6;
    previous:string;
    next:string;
    selectedUsers: MUser;
    selectedUsersDetails: [number,number,number, number, MUser[]];

    constructor(private route: ActivatedRoute,
                private router: Router,
                private userService: UserService,
                private userUserService: UserUserService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        this.getUsers();
        this.paginationClass();
    }

    selectUser(index: number){
        this.selectedUsers = this.users[index];
        this.selectedUsersDetails = [index, this.page, this.size, this.totalPage, this.users];
    }

    setIndex(index: number){
        if(index == -1){
            this.selectedUsersDetails[0] = this.size-1;
        }else{
            this.selectedUsersDetails[0] = 0;
        }
    }

    paginationClass(){
        if(this.page!=0 && this.page+1<this.totalPage){
            this.previous = "col-xxl-9 col-xl-9 col-lg-9 col-md-9 col-sm-9";
            this.next = "col-xxl-3 col-xl-3 col-lg-3 col-md-3 col-sm-3";
        }else if (this.page==0 && this.page+1<this.totalPage){
            this.previous = "";
            this.next = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
        }else if(this.page!=0 && this.page+1==this.totalPage){
            this.previous = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
            this.next = "";
        }else{
            this.previous = "";
            this.next = "";
        }
    }

    setPage(page: number){
        this.page += page;
        this.pagination();
        this.paginationClass();
    }

    searchInput(){
        this.page=0;
        this.pagination();
    }

    setStatus (userId: number){
        let status: string = this.friends[userId];
        if (status == STATUS.REQUEST){
            let mUserUser: MUserUser = new MUserUser();
            mUserUser.userId = this.authenticationService.getUser().id;
            mUserUser.friendId = userId;
            mUserUser.status = false;
            this.userUserService.createUserUser(mUserUser).subscribe();
            this.friends[userId] = STATUS.SENT;
        }else if (status == STATUS.FOLLOW){
            this.messages[userId] = MESSAGE.UNFOLLOW;
        }else if (status == STATUS.SENT){
            this.messages[userId] = MESSAGE.CANCELREQUEST;
        }else if (status == STATUS.PENDING){
            this.messages[userId] = MESSAGE.ALLOWREQUEST;
        }
    }

    acceptButton(userId: number){
        if (this.messages[userId] == MESSAGE.CANCELREQUEST || this.messages[userId] == MESSAGE.UNFOLLOW){
            this.deleteUserUser(userId);
        }else if (this.messages[userId] == MESSAGE.ALLOWREQUEST){
            let mUserUser: MUserUser = new MUserUser();
            mUserUser.userId = userId
            mUserUser.friendId = this.authenticationService.getUser().id;
            mUserUser.status = true;
            this.userUserService.updateUserUser(mUserUser).subscribe();
            this.friends[userId] = STATUS.FOLLOW;
        }
        this.messages[userId] = null;
    }

    refuseButton(userId:number){
       if (this.messages[userId] == MESSAGE.ALLOWREQUEST){
            this.deleteUserUser(userId);
        }
        this.messages[userId] = null;
    }

    private deleteUserUser(userId: number){
        this.userUserService.deleteUserUser(userId, this.authenticationService.getUser().id).subscribe();
        this.friends[userId] = STATUS.REQUEST;
    }

    private searchUsers(){
        this.userService.getPageableUserByLogin(this.login, this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.users = response;
            this.getStatus(response);
            this.searchTotalPages();
            this.getURL(response);
        });
    }

    private pagination(){
        if(this.login == ""){
            this.getUsers();
        }else{
            this.searchUsers();
        }
    }

    getUsers(){
        this.userService.getPageableUser(this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.users = response;
            this.getURL(response);
            this.getStatus(response);
            this.totalPages();
        });
    }

    private getURL(users: MUser[]){
        users.forEach((user) => {
            this.images[user.userId] = this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;png;base64,' + user.userPhoto);
        });
    }

    private statusValue (statusBD: boolean, friend: boolean): string {
        let status: string;
        if (statusBD) {
            status = STATUS.FOLLOW;
        } else if (friend) {
            status = STATUS.PENDING;
        }else{
            status = STATUS.SENT;
        }
        return status;
    }

    private getStatus (mUser: MUser[]) {
        mUser.forEach((mUser) => {
            this.userUserService.getUserUser(mUser.userId, this.authenticationService.getUser().id).subscribe((response) => {
                if (response != null) {
                    this.friends[mUser.userId] = this.statusValue(response.status,false);
                } else {
                    this.friendStatus(mUser);
                }
            });
        });
    }

    private friendStatus(mUser: MUser){
            this.userUserService.getUserUser(this.authenticationService.getUser().id,mUser.userId).subscribe((response) => {
                if (response != null) {
                    this.friends[mUser.userId] = this.statusValue(response.status, true);
                }else{
                    this.friends[mUser.userId] = STATUS.REQUEST;
                }
            });
    }

    private totalPages() {
        this.userService.countUsers(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private searchTotalPages(){
        this.userService.countSearchUsers(this.login, this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }
}