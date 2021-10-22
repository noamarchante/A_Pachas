import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserGroupService} from "../../../services/userGroup.service";
import {UserService} from "../../../services/user.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {MUser} from "../../../services/entities/MUser";
import {UserGroupUserService} from "../../../services/userGroupUser.service";
import {UserUserService} from "../../../services/userUser.service";
import {MUserGroup} from "../../../services/entities/MUserGroup";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
    selector: 'app-createGroup',
    templateUrl: './createGroup.component.html',
    styleUrls: ['./createGroup.component.css']
})
export class CreateGroupComponent implements OnInit {

    defaultImage = "./assets/group.jpg";
    defaultUserImage = "./assets/user.png";
    friends: MUser[] = null;
    imageFormat: boolean = null;
    groupMembers: number[] = [];
    imageColor:string="";
    imageText: string;
    title: string = "CREAR GRUPO";
    userGroup: MUserGroup;
    _userGroup: MUserGroup;
    @Output()
    eventSave = new EventEmitter<boolean>();
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
        this.getFriends();
    }

    get editUserGroup(){
        return this._userGroup;
    }


    @Input() set editUserGroup(userGroup: MUserGroup){
        this._userGroup = userGroup;
        if (this._userGroup != undefined){
            this.title = "Editar grupo";
            this.userGroup = this._userGroup;
        }else{
            this.userGroup = new MUserGroup();
        }
    }

    onCreate() {
        this.userGroup.userGroupOwner = this.authenticationService.getUser().id;
        this.groupMembers.push(this.authenticationService.getUser().id);
        this.userGroupService.createUserGroup(this.userGroup).subscribe((response) => {
            this.groupMembers.forEach((id)=> {
                this.userGroupUserService.createUserGroupUser(response,id).subscribe(() =>{
                    this.eventSave.emit();
                });
            });
            document.getElementById("closeButton").click();
        });
    }

    /*onEdit(){
        this.userGroupService.editUserGroup(this.userGroup).subscribe((response) => {
            this.groupMembers.forEach((id)=> {
                this.userGroupUserService.editUserGroupUser(response,id).subscribe(() =>{
                    this.eventSave.emit();
                });
            });
            this.closeModal();
        });
    }*/

    getImage(event): any {
        const file = event.target.files[0];
        if (this.imageFormatTest(file)){
            this.getBase64(file).then((image: any) => {
                this.userGroup.userGroupPhoto = image.base;
            });
            this.imageFormat = true;
        }else{
            this.imageFormat = false;
        }
    }

    closeModal(){
        this.userGroup = new MUserGroup();
        this.groupMembers = [];
        this.imageFormat = null;
    }

    changeStyle($event){
        if ($event.type == "mouseover"){
            this.imageColor = "imageColor";
            this.imageText = "imageText";
        }else{
            this.imageColor = "";
            this.imageText= "";
        }
    }

    private imageFormatTest(file:any): boolean{
        if(file.type.toString() == "image/jpeg" || file.type.toString() == "image/png"){
            return true;
        }
        return false;
    }

    private getBase64 = async ($event: any) => new Promise((resolve, reject) => {
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
    });

    private getFriends(){
        this.userUserService.getFriends(this.authenticationService.getUser().id).subscribe((response) => {
            this.friends = response;
        });
    }

   /* private getMembers(){
        this.userGroupUserService.getUsersByUserGroupId(this._editUserGroup.userGroupId).subscribe((response) =>{
            response.forEach((user)=>{
                this.groupMembers.push(user.userId);
            });
        });
    }*/
}