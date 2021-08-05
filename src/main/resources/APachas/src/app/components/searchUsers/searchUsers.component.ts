import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from "../../services/user.service";
import {MUser} from "../../services/entities/MUser";
import {MUserUser} from "../../services/entities/MUserUser";
import {UserUserService} from "../../services/userUser.service";
import {AuthenticationService} from "../../services/authentication.service";
import {DomSanitizer} from "@angular/platform-browser";


export enum STATUS {
    REQUEST = 'Solicitar amistad', PENDING = 'Solicitud pendiente', FOLLOW = 'Siguiendo', SENT = 'Solicitud enviada'
}

export enum MESSAGE{
    CANCELREQUEST = '¿Cancelar solicitud?', UNFOLLOW = '¿Dejar de seguir?', ALLOWREQUEST = '¿Aceptar solicitud?'
}

@Component({
    selector: 'app-friends',
    templateUrl: './searchUsers.component.html',
    styleUrls: ['./searchUsers.component.css']
})
export class SearchUsersComponent implements OnInit {
    login = "";
    users: MUser[] = [];
    images: {[index:number]: any;} = {};
    defaultImage = "./assets/user.png";
    messages: {[index: number]: any;} = {};
    friends: {[index: number]: any;} = {};
    totalPage = 0;
    page = 0;
    private size = 6;
    private authUser: MUser;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private userService: UserService,
                private userUserService: UserUserService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        this.getAuthUser();
    }

    setPage(page: number){
        this.page += page;
        this.pagination();
    }

    searchInput(){
        this.page=0;
        this.pagination();
    }

    setStatus (userId: number){
        let status: string = this.friends[userId];
        if (status == STATUS.REQUEST){
            let mUserUser: MUserUser = new MUserUser();
            mUserUser.userId = this.authUser.userId;
            mUserUser.friendId = userId;
            mUserUser.status = false;
            this.userUserService.create(mUserUser).subscribe();
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
            mUserUser.friendId = this.authUser.userId;
            mUserUser.status = true;
            this.userUserService.update(mUserUser).subscribe();
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
        this.userUserService.delete(userId, this.authUser.userId).subscribe();
        this.friends[userId] = STATUS.REQUEST;
    }

    private search(){
        this.userService.getPageableUser(this.login, this.authenticationService.getUser().login, this.page, this.size).subscribe((response) => {
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
            this.search();
        }
    }

    private getUsers(){
        this.userService.getPageable(this.authenticationService.getUser().login, this.page, this.size).subscribe((response) => {
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
            this.userUserService.get(mUser.userId, this.authUser.userId).subscribe((response) => {
                if (response != null) {
                    this.friends[mUser.userId] = this.statusValue(response.status,false);
                } else {
                    this.friendStatus(mUser);
                }
            });
        });
    }

    private friendStatus(mUser: MUser){
            this.userUserService.get(this.authUser.userId,mUser.userId).subscribe((response) => {
                if (response != null) {
                    this.friends[mUser.userId] = this.statusValue(response.status, true);
                }else{
                    this.friends[mUser.userId] = STATUS.REQUEST;
                }
            });
    }

    private totalPages() {
        this.userService.count(this.authenticationService.getUser().login).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private searchTotalPages(){
        this.userService.searchCount(this.login, this.authenticationService.getUser().login).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private getAuthUser(){
        this.userService.getByLogin(this.authenticationService.getUser().login).subscribe((response) => {
            this.authUser = response;
            this.getUsers();
        });
    }
}