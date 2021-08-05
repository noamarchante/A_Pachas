/*
COMPONENT -> Bloque de código re-utilizable: CSS + HTML + TypeScript
 */
import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  login: string;
  password: string;
  private return = '';

	constructor(private authenticationService: AuthenticationService,
				private route: ActivatedRoute,
				private router: Router) {
	}

	ngOnInit() {
		this.route.queryParams
		  .subscribe(params => this.return = params['return'] || '');
	}

	logIn() {
		this.authenticationService.checkCredentials(this.login, this.password).subscribe((s: any) =>{
			this.authenticationService.logIn(this.login, this.password, s.headers.get("Authorization"));
			this.router.navigateByUrl(this.return);
		});
	}
}
