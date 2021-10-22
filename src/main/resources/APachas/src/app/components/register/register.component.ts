import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from "../../services/user.service";
import {MUser} from "../../services/entities/MUser";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
  private mUser: MUser = new MUser();
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
    this.mUser.userLogin = this.login;
    this.mUser.userPassword = this.password;
    this.mUser.userName = this.name;
    this.mUser.userEmail = this.email;
    this.mUser.userSurname = this.surname;
    if(this.password == this.passwordConfirm) {
      this.userService.createUser(this.mUser).subscribe();
      this.router.navigateByUrl(this.return);
    }
  }
}
