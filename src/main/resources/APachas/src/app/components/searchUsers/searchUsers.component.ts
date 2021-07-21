import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from "../../services/user.service";
import {MUser} from "../../services/entities/MUser";
import {MUserUser} from "../../services/entities/MUserUser";
import {UserUserService} from "../../services/userUser.service";
import {AuthenticationService} from "../../services/authentication.service";
import {DomSanitizer} from "@angular/platform-browser";


export enum STATUS {
    REQUESTSTATUS = 'Solicitar amistad', PENDINGSTATUS = 'Solicitud pendiente', FRIENDSSTATUS = 'Siguiendo'
}

@Component({
    selector: 'app-friends',
    templateUrl: './searchUsers.component.html',
    styleUrls: ['./searchUsers.component.css']
})
export class SearchUsersComponent implements OnInit {
    users: MUser[] = [];
    friends: {[index: number]: any;} = {};
    images: {[index:number]: any;} = {};
    authUser: MUser;
    login = "";
    totalPage = 0;
    defaultImage = "./assets/user.png";
    page = 0;
    size = 6;
    return = 'home';

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
    pagination(){
        if(this.login == ""){
        this.getUsers();
        }else{
            this.search();
        }
    }
    searchInput(){
        this.page=0;
        this.pagination();
    }
    search(){
        this.userService.getPageableUser(this.login, this.authenticationService.getUser().login, this.page, this.size).subscribe((response) => {
            this.users = response;
            this.getStatus(response);
            this.searchTotalPages();
            this.getURL(response);
        });
    }

    getImage(user: MUser): string{
        let image: string = this.images[user.userId];
        if (user.userPhoto != null){
            return image
        }else{
            return this.defaultImage;
        }
    }

    setStatus (userId: number){
        let status: string = this.friends[userId];
        if (status == STATUS.REQUESTSTATUS){
            let mUserUser: MUserUser = new MUserUser();
            mUserUser.userId = this.authUser.userId;
            mUserUser.friendId = userId;
            mUserUser.status = false;
            this.userUserService.create(mUserUser).subscribe();
            this.friends[userId] = STATUS.PENDINGSTATUS;
        }else{
            this.userUserService.delete(userId, this.authUser.userId).subscribe();
            this.friends[userId] = STATUS.REQUESTSTATUS;
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

    private statusValue (statusBD: boolean): string {
        let status: string;
        if (statusBD) {
            status = STATUS.FRIENDSSTATUS;
        } else {
            status = STATUS.PENDINGSTATUS;
        }
        return status;
    }

    private getStatus (mUser: MUser[]) {
        mUser.forEach((mUser) => {
            this.userUserService.get(mUser.userId, this.authUser.userId).subscribe((response) => {
                if (response != null) {
                    this.friends[mUser.userId] = this.statusValue(response.status);
                } else {
                    this.friends[mUser.userId] = STATUS.REQUESTSTATUS;
                }
            });
        });
    }

    private totalPages () {
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