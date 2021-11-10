import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserGroupService} from "../../../services/userGroup.service";
import {UserService} from "../../../services/user.service";
import {UserGroupUserService} from "../../../services/userGroupUser.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {NotificationService} from "../../../modules/notification/services/notification.service";
import {MUser} from "../../../models/MUser";
import {MUserGroup} from "../../../models/MUserGroup";

@Component({
    selector: 'app-detailGroup',
    templateUrl: './detailGroup.component.html',
    styleUrls: ['./detailGroup.component.css']
})
export class DetailGroupComponent implements OnInit {

    @Output()
    eventDelete = new EventEmitter<boolean>();

    @Output()
    eventDetail = new EventEmitter<number>();

    defaultUserImage: string = "./assets/user.png";
    defaultImage: string = "./assets/group.jpg";

    groupMembers: MUser[] = [];
    groupMembersStored: MUser[] = [];
    pageMember = 0;
    sizeMember = 6;
    totalMembers: number=0;
    more: string = "Ver más ...";
    message: string = "";

    previousUserGroup: string="";
    nextUserGroup: string="";

    _previous: boolean = false;
    _next: boolean = false;
    _userGroup: MUserGroup = new MUserGroup();

    private return = 'groups';

    constructor(private route: ActivatedRoute,
                private router: Router,
                private userGroupService: UserGroupService,
                private userService: UserService,
                private userGroupUserService: UserGroupUserService,
                private authenticationService: AuthenticationService,
                private notificationService: NotificationService
    ) {
    }

    ngOnInit() {
        this.paginationUserGroupClass();
    }

    messageValue(){
        if (this.authenticationService.getUser().id == this.userGroup.userGroupOwner){
            this.message = "¿Estás seguro de que deseas eliminar el grupo?";
        }else{
            this.message = "¿Estás seguro de que quieres salir del grupo?";
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


    get userGroup(){
        return this._userGroup;
    }

    @Input() set userGroup (userGroup: MUserGroup) {
        if (userGroup != undefined) {
            this._userGroup = userGroup;
            if (this.userGroup.userGroupId != null){
                this.getUsersGroupsUsers(this.userGroup.userGroupId);
                this.moreMembers(this.userGroup.userGroupId);
            }else{
                this._userGroup = new MUserGroup();
            }
        }
        this.groupMembers = [];
        this.groupMembersStored = [];
    }

    onDelete($event){
        if($event.valueOf()){
            if (this.userGroup.userGroupOwner == this.authenticationService.getUser().id){
                this.userGroupService.deleteUserGroup(this.userGroup).subscribe(()=>{
                        this.eventDelete.emit();
                        this.notificationService.success("Grupo eliminado", "Se ha eliminado el grupo correctamente.");

                    }
                );
            }else{
                this.userGroupUserService.deleteUserGroupUser(this.userGroup.userGroupId, this.authenticationService.getUser().id).subscribe(() => {
                    this.eventDelete.emit();
                    this.notificationService.success("Eliminado del grupo", "Ya no eres miembro de este grupo.");
                });
            }
        }
    }

    edit(): boolean{
        if (this.userGroup.userGroupOwner == this.authenticationService.getUser().id){
            return true;
        }else{
            return false;
        }
    }

    ownerLabel(userId:number):string{
        let value:string = "";
        if (userId == this.userGroup.userGroupOwner){
            value = "Administrador";
        }
        return value;
    }

    ownerBorder(userId: number):string{
        if(userId == this.userGroup.userGroupOwner){
            return "owner";
        }
    }

    setPageUserGroup(number: number){
        this.eventDetail.emit(number);
    }


    getUsersGroupsUsers(userGroupId:number){
        this.userGroupUserService.getPageableUsersByUserGroupId(userGroupId,this.pageMember, this.sizeMember).subscribe((response) => {
            this.groupMembers.push(...response);
            this.paginationUserGroupClass();
            this.setMoreLabel();
        });
    }

    moreMembers(userGroupId:number){
        this.userGroupUserService.countUsersGroupsUsersByUserGroupId(userGroupId).subscribe((members)=>{
            this.totalMembers = members;
        });
    }

    setMoreLabel(){
        if (this.groupMembers.length == this.totalMembers){
            this.more = "... Ver menos";
        }else{
            this.more = "Ver más ...";
        }
    }

    getMoreMembers(){
        if (this.groupMembers.length == this.totalMembers){
            this.groupMembersStored = this.groupMembers;
            this.groupMembers = this.groupMembers.slice(0,this.sizeMember);
        }else{
            if (this.groupMembersStored.length ==0){
                this.pageMember +=1;
                this.getUsersGroupsUsers(this.userGroup.userGroupId);
            }else{
                this.groupMembers = this.groupMembersStored;
            }
        }
        this.setMoreLabel();
    }

    private paginationUserGroupClass(){
        if(this._previous && this._next){
            this.previousUserGroup = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
            this.nextUserGroup = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
        }else if (!this._previous && this._next){
            this.previousUserGroup = "";
            this.nextUserGroup = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
        }else if(this._previous && !this._next){
            this.previousUserGroup = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12";
            this.nextUserGroup = "";
        }else{
            this.previousUserGroup = "";
            this.nextUserGroup = "";
        }
    }
}