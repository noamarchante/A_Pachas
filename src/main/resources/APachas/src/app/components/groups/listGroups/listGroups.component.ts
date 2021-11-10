import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DomSanitizer} from "@angular/platform-browser";
import {UserService} from "../../../services/user.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {UserGroupService} from "../../../services/userGroup.service";
import {MUserGroup} from "../../../models/MUserGroup";

@Component({
    selector: 'app-groups',
    templateUrl: './listGroups.component.html',
    styleUrls: ['./listGroups.component.css']
})
export class ListGroupsComponent implements OnInit {
    groupName = "";
    groups: MUserGroup[] = [];
    images: {[index:number]: any;} = {};
    defaultImage = "./assets/group.jpg";
    totalPage:number= 0;
    page: number= 0;
    edit: boolean = false;
    selectedUserGroup: MUserGroup = new MUserGroup();
    size: number= 6;
    index: number;
    previous: boolean;
    next:boolean;
    previousClass:string;
    nextClass:string;
    pageDirection: number;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private userService: UserService,
                private userGroupService: UserGroupService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        this.getGroups();
        this.paginationClass();
    }

    setGroup(){
        this.selectedUserGroup = new MUserGroup();
    }

    selectUserGroup(index:number){
        this.selectedUserGroup = this.groups[index];
        this.index = index;
    }

    setSelectedUserGroupPage(){
        if (this.pageDirection != undefined){
            if (this.pageDirection == -1){
                this.index = this.size-1;
                this.selectedUserGroup = this.groups[this.index];
            }else if (this.pageDirection == 1){
                this.index = 0;
                this.selectedUserGroup = this.groups[this.index];
            }
        }
    }

    getGroups(){
        this.userGroupService.getPageableUserGroup(this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.groups = response;
            this.setSelectedUserGroupPage();
            this.getURL(response);
            this.totalPages();
        });
    }

    paginationClass(){
        if(this.page!=0 && this.page+1<this.totalPage){
            this.previousClass = "col-xxl-9 col-xl-9 col-lg-9 col-md-9 col-sm-9";
            this.nextClass = "col-xxl-3 col-xl-3 col-lg-3 col-md-3 col-sm-3";
        }else if (this.page==0 && this.page+1<this.totalPage){
            this.previousClass = "";
            this.nextClass = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
        }else if(this.page!=0 && this.page+1==this.totalPage){
            this.previousClass = "col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12";
            this.nextClass = "";
        }else{
            this.previousClass = "";
            this.nextClass = "";
        }
    }

    setPage(page: number){
        this.page += page;
        this.pagination();
        this.paginationClass();
    }

    private pagination(){
        if(this.groupName == ""){
            this.getGroups();
        }else{
            this.search();
        }
    }

    private getURL(groups: MUserGroup[]){
        groups.forEach((group) => {
            this.images[group.userGroupId] = this.sanitizer.bypassSecurityTrustUrl(group.userGroupPhoto);
        });
    }

    private search(){
        this.userGroupService.getPageableUserGroupByGroupName(this.groupName, this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.groups = response;
            this.setSelectedUserGroupPage();
            this.searchTotalPages();
            this.getURL(response);
        });
    }

    searchInput(){
        this.page=0;
        this.pagination();
    }

    private totalPages() {
        this.userGroupService.countUsersGroups(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private searchTotalPages(){
        this.userGroupService.countSearchUsersGroups(this.groupName, this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    getNext (): boolean{
        if (this.page != this.totalPage-1 || this.groups[this.index+1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    getPrevious():boolean{
        if (this.page != 0 || this.groups[this.index-1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    setSelectedUserGroup(event: number) {
        this.pageDirection = event.valueOf();
        if ( this.groups[this.index + event.valueOf()] != undefined){
                this.selectUserGroup(this.index + event.valueOf());
        }else{
            this.setPage(event.valueOf());

        }
    }
}