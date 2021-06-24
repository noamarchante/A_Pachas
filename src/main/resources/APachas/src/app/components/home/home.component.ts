import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  loggedUser: string;

  constructor(private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    if (this.authenticationService.getUser().authenticated) {
      this.loggedUser = this.authenticationService.getUser().login;
    }
  }
}