import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserGroupService} from "../../services/userGroup.service";
import {UserService} from "../../services/user.service";
import {AuthenticationService} from "../../services/authentication.service";
import {MUser} from "../../services/entities/MUser";
import {User} from "../../models/User";
import {UserGroupUserService} from "../../services/userGroupUser.service";
import {UserUserService} from "../../services/userUser.service";
import {MUserGroup} from "../../services/entities/MUserGroup";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
    selector: 'app-createGroup',
    templateUrl: './createGroup.component.html',
    styleUrls: ['./createGroup.component.css']
})
export class CreateGroupComponent implements OnInit {


    groupName = "";
    groupDescription = "";
    groupPhoto: string;

    friends: MUser[] = null;
    groupMembers: MUser[] = [];

    private userGroup: MUserGroup = new MUserGroup();
    private authUser: MUser;
    private return = 'groups';


    constructor(private route: ActivatedRoute,
                private router: Router,
                private userGroupService: UserGroupService,
                private userService: UserService,
                private userGroupUserService: UserGroupUserService,
                private userUserService: UserUserService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer
    ) {
    }

    ngOnInit() {
        this.getAuthUser();
    }

    onCreate() {
        this.userGroup.userGroupName = this.groupName;
        this.userGroup.userGroupDescription = this.groupDescription;
        this.userGroup.userGroupPhoto = this.groupPhoto;
        this.userGroup.userGroupOwner = this.authUser.userId;
        this.groupMembers.push(this.authUser);
        this.userGroupService.create(this.userGroup).subscribe((response) => {
            this.groupMembers.forEach((user)=> {
                console.log(response);
                console.log(user.userId);
                this.userGroupUserService.create(response, user.userId).subscribe();

            });
        });

    }

    getImage(event): any {
        const file = event.target.files[0];
        this.getBase64(file).then((image: any) => {
            this.groupPhoto = image.base;
        });
    }

    getBase64 = async ($event: any) => new Promise((resolve, reject) => {
        try {
            const unsafeImg = window.URL.createObjectURL($event);
            const image = this.sanitizer.bypassSecurityTrustUrl(unsafeImg);
            const reader = new FileReader();
            reader.readAsDataURL($event);

            reader.onload = () => {
                resolve({
                    blob: $event,
                    image,
                    base: reader.result
                });
            };
            reader.onerror = error => {
                resolve({
                    blob: $event.blob,
                    image,
                    base: null
                });
            };
        } catch (e) {
            return null;
        }
    })

    private getFriends(authUser: MUser){
        this.userUserService.getFriends(authUser.userId).subscribe((response) => {
            this.friends = response;
        });
    }

    private getAuthUser(){
        this.userService.getByLogin(this.authenticationService.getUser().login).subscribe((response) => {
            this.authUser = response;
            this.getFriends(this.authUser);
        });
    }
}