import {Component, EventEmitter, Input, OnInit, Output, ViewChildren} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from "../../../services/user.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {MUser} from "../../../models/MUser";
import {UserUserService} from "../../../services/userUser.service";
import {DomSanitizer} from "@angular/platform-browser";
import {NotificationService} from "../../../modules/notification/services/notification.service";
import {MEvent} from "../../../models/MEvent";
import {UserEventService} from "../../../services/userEvent.service";
import {EventService} from "../../../services/event.service";
import {MGroup} from "../../../models/MGroup";
import {GroupUserService} from "../../../services/groupUser.service";
import {MGroupMembers} from "../../../models/MGroupMembers";

@Component({
    selector: 'app-createEvent',
    templateUrl: './createEvent.component.html',
    styleUrls: ['./createEvent.component.css']
})
export class CreateEventComponent implements OnInit {

    defaultImage = "./assets/event.jpg";
    defaultUserImage = "./assets/user.png";
    defaultGroupImage = "./assets/group.jpg";

    friends: MUser[];
    groups: MGroup[];
    selectPartakers: MGroupMembers[];

    imageFormat: boolean;
    eventPartakers: number[];
    eventGroups: number[];
    imageColor:string="";
    imageText: string;
    title: string = "CREAR EVENTO";
    _event: MEvent;
    flag: boolean = false;
    @Output()
    eventSave = new EventEmitter<boolean>();
    private return = 'events';

    public initialDate;
    public finalDate;
    public options: any = {
        autoApply: false,
        alwaysShowCalendars: true,
        applyButtonClasses: "btn-primary applyDate",
        buttonClasses: "btn btn-sm",
        cancelClass: "btn-default cancelDate",
        drops: "up",
        locale: {
            format: 'DD/MM/yyyy HH:mm',
            "firstDay": 1
        },
        minDate: new Date(),
        maxDate: undefined,
        opens: 'left',
        showDropdowns: true,
        timePicker: true
    };

    constructor(private route: ActivatedRoute,
                private router: Router,
                private eventService: EventService,
                private userService: UserService,
                private groupUserService: GroupUserService,
                private userEventService: UserEventService,
                private userUserService: UserUserService,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer,
                private notificationService: NotificationService
    ) {
    }

    ngOnInit() {
        this.eventPartakers = [];
        this.eventGroups = [];
        this.friends = [];
        this.groups = [];
        this.selectPartakers = [];
    }

    public applyDate(): void {
        this.event.eventStart =new Date(this.initialDate.valueOf());
        this.event.eventEnd = new Date(this.finalDate.valueOf());
    }

    public selectedDate(value: any): void {
        this.initialDate = new Date(value.start);
        this.finalDate = new Date(value.end);
    }

    public cancelDate(): void {
        this.initialDate = undefined;
        this.finalDate = undefined;
    }

    get event(){
        return this._event;
    }

    @Input() set event(event: MEvent){
        if (event.eventId != undefined){
            this._event = event;
            this.title = "Editar evento";
            this.getPartakers();
        }else{
            this._event = new MEvent();
            this.title = "Crear evento";
        }

        this.eventPartakers = [];
        this.eventGroups = [];
        this.selectPartakers = [];
        this.getGroups();
    }

    onSubmit(){
        if (this.event.eventId != undefined){
            this.onEdit();
        }else{
            this.onCreate();
        }
    }

    onCreate(){

        this.event.eventOwner = this.authenticationService.getUser().id;
        this.eventPartakers.push(this.authenticationService.getUser().id);
        this.eventService.createEvent(this.event).subscribe((response) => {
            this.eventPartakers.forEach((id)=> {
                this.userEventService.createUserEvent(response,id).subscribe(() =>{
                    this.eventSave.emit();
                });
            });
            this.closeModal();
            document.getElementById("closeButton").click();
            this.notificationService.success("Nuevo evento creado", "Se ha creado el evento correctamente.");
        });


    }

    onEdit(){
        this.eventPartakers.push(this.authenticationService.getUser().id);
        this.eventService.editEvent(this.event).subscribe(() => {
            console.log(this.eventPartakers);
            this.userEventService.editUserEvent(this.event.eventId, this.eventPartakers).subscribe();
            this.eventSave.emit();
            this.closeModal();
            document.getElementById("closeButton").click();
            this.notificationService.success("Evento editado", "Se ha editado el evento correctamente.");
        });
    }

    getImage(event): any {
        const file = event.target.files[0];
        if (this.imageFormatTest(file)){
            this.getBase64(file).then((image: any) => {
                this.event.eventPhoto = image.base;
            });
            this.imageFormat = true;
        }else{
            this.imageFormat = false;
        }
    }

    closeModal(){
        this.event = new MEvent();
        this.eventPartakers = [];
        this.eventGroups = [];
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

    private getFriends(users: number[]){
        this.userUserService.getFriends(this.authenticationService.getUser().id).subscribe((response) => {
            this.friends = response;
            response.forEach(user=>{
                if (!Object.values(users).includes(user.userId)){
                    let member: MGroupMembers = new MGroupMembers();
                    member.groupId = 0;
                    member.groupName = "Otros";
                    member.groupPhoto = null;
                    member.userId = user.userId;
                    member.userLogin = user.userLogin;
                    member.userPhoto = user.userPhoto;
                    this.selectPartakers.push(member);
                    this.selectPartakers = [... this.selectPartakers];
                }
            });
            this.selectPartakers = this.selectPartakers.sort((a, b) => {

                // if (a.groupId == 0) {
                //     return 1;
                // }

                if (a.groupName > b.groupName) {
                    return -1;
                } else {
                    if (a.groupName == b.groupName) {
                        if (a.userLogin > b.userLogin) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        return 1;
                    }
                }
            })
        });
    }

    private getGroups(){
        let users: number[] = [];
        this.groupUserService.getGroups(this.authenticationService.getUser().id).subscribe((response) => {
            this.groups = response;
            this.groups.forEach(group => {
                this.groupUserService.getMembers(group.groupId).subscribe((response) =>{
                    response.forEach(user=>{
                        let member: MGroupMembers = new MGroupMembers();
                        if (response.length > 1 && user.userId != this.authenticationService.getUser().id){
                            member.groupId = group.groupId;
                            member.groupName = group.groupName;
                            member.groupPhoto = group.groupPhoto;
                            member.userId = user.userId;
                            member.userLogin = user.userLogin;
                            member.userPhoto = user.userPhoto;
                            users.push(user.userId);
                            this.selectPartakers.push(member);

                            this.selectPartakers = [... this.selectPartakers];
                        }
                        return;
                    });
                });
                return;
            });
            this.getFriends(users);
        });
    }

    private getPartakers(){
        this.userEventService.getPartakers(this.event.eventId).subscribe((response) =>{
            response.forEach((user)=>{
                if(user.userId != this.event.eventOwner){
                    this.eventPartakers.push(user.userId);
                }
            });
            this.eventPartakers = [... this.eventPartakers];
        });

    }

    public getPartakersByGroupId(id: number): MGroupMembers {
        return this.selectPartakers.find(group => group.groupId === id);
    }

    public changeSelect(event) {
       /*var options = document.getElementsByClassName("ng-option");
       var totalOptions = options.length;
       var index;
       for (index =0 ; index < totalOptions; index++){
           if (options.item(index).getElementsByTagName("span").item(0).id.endsWith(event.valueOf()[0].userId.toString())){
               options.item(index).getElementsByTagName("span").item(0).click();
           }
       }*/

            /*var i;
            for(i=0;i<totalOptions.length;i++) {
                if(totalOptions[i].classList.value.includes('ng-option-marked')) {
                    this.selectedCars = i;
                    totalOptions[i].click();

                }
            }*/

            event.forEach(userSelected => {
                this.selectPartakers.map(user => {
                    if (user.userId == userSelected.userId) {
                        user.disabled = true;
                    }
                });
                this.selectPartakers = [... this.selectPartakers];
        });
    }

    public removePartaker(event) {
            this.selectPartakers.map(user => {
                if (user == event.userId) {
                    user.disabled = false;
                }
            });
        this.selectPartakers = [... this.selectPartakers];
    }

    public clearPartakers() {
        this.eventPartakers = [];
    }

    public unselectGroup(groupID): void {
        let userGroup = [];
        this.selectPartakers.map(user => {
            if (user.groupId == groupID && this.eventPartakers.includes(user.userId)) {
                userGroup.push(user.userId);
                this.eventPartakers = this.eventPartakers.filter(u => u != user.userId);
            }
        });

        this.selectPartakers.map(user => {
            if (userGroup.includes(user.userId)) {
                user.disabled = false;
            }
        });

        this.selectPartakers = [... this.selectPartakers];
        this.eventPartakers = [... this.eventPartakers];
    }
}