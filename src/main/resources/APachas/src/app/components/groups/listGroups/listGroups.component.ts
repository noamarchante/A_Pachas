import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DomSanitizer} from "@angular/platform-browser";
import {MUserGroup} from "../../../services/entities/MUserGroup";
import {UserService} from "../../../services/user.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {UserGroupService} from "../../../services/userGroup.service";

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
    totalPage = 0;
    page = 0;
    edit: boolean = false;
    selectedUserGroups: MUserGroup;
    selectedUserGroupsDetails: [number,number,number, number, MUserGroup[]];
    private size = 6;
    previous:string;
    next:string;

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


    selectUserGroup(index:number){
        this.selectedUserGroups = this.groups[index];
        this.selectedUserGroupsDetails = [index, this.page, this.size, this.totalPage, this.groups];
    }

    getGroups(){
        this.userGroupService.getPageableUserGroup(this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.groups = response;
            if(this.selectedUserGroupsDetails != undefined){
                this.selectedUserGroupsDetails[4] = response;
                this.selectedUserGroupsDetails = [... this.selectedUserGroupsDetails];
                if (this.selectedUserGroupsDetails[4] == response){
                    //document.getElementById("detailGroupCard").click();
                }
            }

            this.getURL(response);
            this.totalPages();
        });
    }

    setIndex(index: number){
        if(index == -1){
            this.selectedUserGroupsDetails[0] = this.size - 1;
        }else{
            this.selectedUserGroupsDetails[0] = 0;
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

    eventDetail(event: [number, number]) {
        console.log("eventDetail")
        console.log(event)
        console.log("page antes")
        console.log(this.page)
        this.setIndex(event.valueOf()[0]);
        this.setPage(event.valueOf()[1]);
        this.selectUserGroup(event.valueOf()[0]);
        console.log(this.selectedUserGroupsDetails)
        console.log("page despues")
        console.log(this.page)
    }
}