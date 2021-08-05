import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from "../../services/user.service";
import {User} from "../../models/User";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
  private user: User = new User();
  name = "";
  surname = "";
  login = "";
  password = "";
  passwordConfirm = "";
  email = "";
  private return = 'login';

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) {
  }
  ngOnInit() {}
  onCreate(){
    this.user.login = this.login;
    this.user.password = this.password;
    this.user.name = this.name;
    this.user.email = this.email;
    this.user.surname = this.surname;
    if(this.password == this.passwordConfirm) {
      this.userService.create(this.user).subscribe();
      this.router.navigateByUrl(this.return);
    }
  }
}
