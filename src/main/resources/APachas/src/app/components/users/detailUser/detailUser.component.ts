import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {MUser} from "../../../services/entities/MUser";
import {MUserGroup} from "../../../services/entities/MUserGroup";

@Component({
    selector: 'app-detailUser',
    templateUrl: './detailUser.component.html',
    styleUrls: ['./detailUser.component.css']
})
export class DetailUserComponent implements OnInit {

    @Output()
    eventSave = new EventEmitter<[number, number]>();
    _selectedUsers: [number,number, number, number, MUser[]];
    _indexUser: number;
    _pageUser: number;
    _sizeUser: number;
    _totalPagesUser: number;
    _users: MUser[];
    user: MUser = new MUser();
    defaultImage: string = "./assets/user.png";

    constructor(private route: ActivatedRoute,
                private router: Router,
                private userService: UserService,
                private authenticationService: AuthenticationService
    ) {
    }

    ngOnInit() {

    }

    get selectedUser() {
        return this._selectedUsers;
    }

    @Input() set selectedUsers(selectedUsers: [number,number, number, number, MUser[]]) {
        this._selectedUsers = selectedUsers;
        if (this._selectedUsers != undefined){
            this._indexUser = this._selectedUsers[0];
            this._pageUser = this._selectedUsers[1];
            this._sizeUser = this._selectedUsers[2];
            this._totalPagesUser = this._selectedUsers[3];
            this._users = this._selectedUsers[4];
            this.user = this._users[this._indexUser];
        }
    }
}