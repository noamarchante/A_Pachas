import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DomSanitizer} from "@angular/platform-browser";
import {UserService} from "../../../services/user.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {MEvent} from "../../../models/MEvent";
import {EventService} from "../../../services/event.service";
import {UserEventService} from "../../../services/userEvent.service";

@Component({
    selector: 'app-events',
    templateUrl: './listEvents.component.html',
    styleUrls: ['./listEvents.component.css']
})
export class ListEventsComponent implements OnInit {
    eventName = "";
    events: MEvent[] = [];
    images: {[index:number]: any;} = {};
    defaultImage: string = "./assets/event.jpg";
    totalPage:number= 0;
    page: number= 0;
    edit: boolean = false;
    selectedEvent: MEvent = new MEvent();
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
                private eventService: EventService,
                private userEventService: UserEventService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {}

    ngOnInit() {
        this.getEvents();
        this.paginationClass();
    }

    setEvent(){
        this.selectedEvent = new MEvent();
    }

    selectEvent(index:number){
        this.selectedEvent = this.events[index];
        this.index = index;
    }

    setSelectedEventPage(){
        if (this.pageDirection != undefined){
            if (this.pageDirection == -1){
                this.index = this.size-1;
                this.selectedEvent = this.events[this.index];
            }else if (this.pageDirection == 1){
                this.index = 0;
                this.selectedEvent = this.events[this.index];
            }
        }
    }

    getEvents(){
        this.userEventService.getPageableEvents(this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.events = response;
            this.setSelectedEventPage();
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
        if(this.eventName == ""){
            this.getEvents();
        }else{
            this.search();
        }
    }

    private getURL(events: MEvent[]){
        events.forEach((event) => {
            this.images[event.eventId] = this.sanitizer.bypassSecurityTrustUrl(event.eventPhoto);
        });
    }

    private search(){
        this.userEventService.getPageableSearchEvents(this.eventName, this.authenticationService.getUser().id, this.page, this.size).subscribe((response) => {
            this.events = response;
            this.setSelectedEventPage();
            this.searchTotalPages();
            this.getURL(response);
        });
    }

    searchInput(){
        this.page=0;
        this.pagination();
    }

    private totalPages() {
        this.userEventService.countEvents(this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    private searchTotalPages(){
        this.userEventService.countSearchEvents(this.eventName, this.authenticationService.getUser().id).subscribe((response) => {
            this.totalPage = Math.ceil(response/this.size);
        });
    }

    getNext (): boolean{
        if (this.page != this.totalPage-1 || this.events[this.index+1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    getPrevious():boolean{
        if (this.page != 0 || this.events[this.index-1] != undefined){
            return true;
        }else{
            return false;
        }
    }

    setSelectedEvent(event: number) {
        this.pageDirection = event.valueOf();
        if ( this.events[this.index + event.valueOf()] != undefined){
            this.selectEvent(this.index + event.valueOf());
        }else{
            this.setPage(event.valueOf());

        }
    }
}