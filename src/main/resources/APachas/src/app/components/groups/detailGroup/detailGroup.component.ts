import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserGroupService} from "../../../services/userGroup.service";
import {UserService} from "../../../services/user.service";
import {MUser} from "../../../services/entities/MUser";
import {UserGroupUserService} from "../../../services/userGroupUser.service";
import {MUserGroup} from "../../../services/entities/MUserGroup";
import {AuthenticationService} from "../../../services/authentication.service";

@Component({
    selector: 'app-detailGroup',
    templateUrl: './detailGroup.component.html',
    styleUrls: ['./detailGroup.component.css']
})
export class DetailGroupComponent implements OnInit {

    @Output()
    eventDelete = new EventEmitter<boolean>();
    @Output()
    eventDetail = new EventEmitter<[number, number]>();
    defaultUserImage: string = "./assets/user.png";
    defaultImage: string = "./assets/group.jpg";
    groupMembers: MUser[] = [];




    totalPageMember = 0;
    _totalPagesUserGroup=0;
    pageMember = 0;
    private sizeMember = 2;

    previousMember:string;
    nextMember:string;
    itemsMember:string;
    previousUserGroup: string;
    nextUserGroup: string;
    //itemsEditUserGroup:string;
    //itemsDeleteUserGroup:string;
    _pageUserGroup: number;
    _sizeUserGroup: number;
    _indexUserGroup: number;
    _userGroups: MUserGroup[];
    _selectedUserGroups: [number, number, number, number, MUserGroup[]];
    userGroup: MUserGroup;
    editing: boolean;
    private return = 'groups';

    constructor(private route: ActivatedRoute,
                private router: Router,
                private userGroupService: UserGroupService,
                private userService: UserService,
                private userGroupUserService: UserGroupUserService,
                private authenticationService: AuthenticationService
    ) {
    }

    ngOnInit() {
        this.userGroup = new MUserGroup();
        this.editing = false;
    }

    get selectedUserGroups() {
        return this._selectedUserGroups;
    }

    @Input() set selectedUserGroups(selectedUserGroups: [number,number, number, number, MUserGroup[]]) {
        this._selectedUserGroups = selectedUserGroups;
        if (this._selectedUserGroups != undefined){
            this._indexUserGroup = this._selectedUserGroups[0];
            this._pageUserGroup = this._selectedUserGroups[1];
            this._sizeUserGroup = this._selectedUserGroups[2];
            this._totalPagesUserGroup = this._selectedUserGroups[3];
            this._userGroups = this._selectedUserGroups[4];
            this.userGroup = this._userGroups[this._indexUserGroup];
            this.paginationUserGroupClass();
            this.getUsersGroupsUsers(this.userGroup.userGroupId);
        }
    }

    delete(){
        if (this.userGroup.userGroupOwner == this.authenticationService.getUser().id){
            this.userGroupService.deleteUserGroup(this.userGroup).subscribe(()=>{
                    this.eventDelete.emit();
                }
            );
        }else{
            this.userGroupUserService.deleteUserGroupUser(this.userGroup.userGroupId, this.authenticationService.getUser().id).subscribe(() => {
                this.eventDelete.emit();
            });
        }

    }

    edit(): boolean{
        if (this.userGroup.userGroupOwner == this.authenticationService.getUser().id){
            return true;
        }else{
            return false;
        }
    }

    owner(userId:number):string{
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

    setPageMember(page: number, userGroupId:number){
        this.pageMember += page;
        this.getUsersGroupsUsers(userGroupId);
        //this.paginationMemberClass();
    }

    setPageUserGroup(index: number){
        this.pageMember = 0;
        this._indexUserGroup += index;
        if (this._indexUserGroup == this._sizeUserGroup || this._indexUserGroup == -1){
            //document.getElementById("closeButton").click();
            if (this._indexUserGroup == this._sizeUserGroup){
                this._indexUserGroup = 0;
            }else if (this._indexUserGroup == -1){
                this._indexUserGroup = this._sizeUserGroup-1;
            }
            this.eventDetail.emit([this._indexUserGroup,index]);
        }
        if(this._userGroups[this._indexUserGroup] != undefined) {
            this.userGroup = this._userGroups[this._indexUserGroup];
            this.getUsersGroupsUsers(this.userGroup.userGroupId);

        }
        this.paginationUserGroupClass();
    }

    private getUsersGroupsUsers(userGroupId:number){
        this.userGroupUserService.getPageableUsersByUserGroupId(userGroupId, this.pageMember, this.sizeMember).subscribe((response) => {
            this.groupMembers = response;
            this.totalPagesMember(userGroupId);
        });
    }

    private totalPagesMember(userGroupId:number) {
        this.userGroupUserService.countUsersGroupsUsersByUserGroupId(userGroupId).subscribe((response) => {
            this.totalPageMember = Math.ceil(response/this.sizeMember);
            //this.paginationMemberClass();
        });
    }

    /*private paginationMemberClass(){
        if(this.pageMember!=0 && this.pageMember+1<this.totalPageMember && this.groupMembers.length==2){
            this.previousMember = "col-xxl-2 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-2";
            this.itemsMember = "col-xxl-4 col-xl-4 col-lg-4 col-md-4 col-sm-4 col-4";
            this.nextMember = "col-xxl-2 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-2";
        }else if (this.pageMember==0 && this.pageMember+1<this.totalPageMember && this.groupMembers.length==2){
            this.previousMember = "";
            this.itemsMember = "col-xxl-5 col-xl-5 col-lg-5 col-md-5 col-sm-5 col-4 itemPosition";
            this.nextMember = "col-xxl-2 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-2";
        }else if(this.pageMember!=0 && this.pageMember+1==this.totalPageMember && this.groupMembers.length==2){
            this.previousMember = "col-xxl-2 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-2";
            this.itemsMember = "col-xxl-5 col-xl-5 col-lg-5 col-md-5 col-sm-5 col-4";
            this.nextMember = "";
        }else if (this.pageMember==0 && this.pageMember+1==this.totalPageMember && this.groupMembers.length==2){
            this.previousMember = "";
            this.itemsMember = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6";
            this.nextMember = "";
        }else if (this.pageMember==0 && this.pageMember+1==this.totalPageMember && this.groupMembers.length==1){
            this.previousMember = "";
            this.itemsMember = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-8 itemPosition";
            this.nextMember = "";
        }else if (this.pageMember!=0 && this.pageMember+1==this.totalPageMember && this.groupMembers.length==1){
            this.previousMember = "col-xxl-2 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-2";
            this.itemsMember = "col-xxl-10 col-xl-10 col-lg-10 col-md-10 col-sm-10 col-6";
            this.nextMember = "";
        }
    }*/

    private paginationUserGroupClass(){
        if((this._pageUserGroup!=0 && this._pageUserGroup+1<this._totalPagesUserGroup) || (this._indexUserGroup > 0 && this._userGroups[this._indexUserGroup+1] != undefined)){
            this.previousUserGroup = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6";
            this.nextUserGroup = "col-xxl-6 col-xl-6 col-lg-6 col-md-6 col-sm-6";
        }else if ((this._pageUserGroup==0 && this._pageUserGroup+1<this._totalPagesUserGroup) || (this._indexUserGroup == 0 && this._userGroups[this._indexUserGroup+1] != undefined)){
            this.previousUserGroup = "";
            this.nextUserGroup = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
        }else if((this._pageUserGroup!=0 && this._pageUserGroup+1==this._totalPagesUserGroup) || (this._indexUserGroup > 0 && this._userGroups[this._indexUserGroup+1] == undefined)){
            this.previousUserGroup = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
            this.nextUserGroup = "";
        }else{
            this.previousUserGroup = "";
            this.nextUserGroup = "";
        }
    }
}