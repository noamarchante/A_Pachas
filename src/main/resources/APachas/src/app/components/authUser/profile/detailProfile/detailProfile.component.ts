import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from "../../../../services/authentication.service";
import {AuthUser} from "../../../../models/AuthUser";
import {MProduct} from "../../../../models/MProduct";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
    selector: 'app-profile',
    templateUrl: './detailProfile.component.html',
    styleUrls: ['./detailProfile.component.css']
})
export class DetailProfileComponent implements OnInit {
    authUser: AuthUser;
    imageProfile: any = null;
    defaultImage: string = "./assets/user.png";


    constructor(private router: Router,
                private authenticationService: AuthenticationService,
                private sanitizer: DomSanitizer) {
    }
    ngOnInit() {
        this.authUser = this.authenticationService.getUser();
        this.getURLProfile();
    }

    private getURLProfile(){
            this.imageProfile = this.sanitizer.bypassSecurityTrustUrl(this.authUser.photo);
    }
}
