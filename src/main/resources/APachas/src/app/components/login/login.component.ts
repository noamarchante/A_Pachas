import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from "../../../environments/environment";

//COMPONENT -> Bloque de código re-utilizable: CSS + HTML + TypeScript
@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})

//COMPONENTE RELATIVO AL LOGIN
export class LoginComponent implements OnInit {

  //VARIABLES NECESARIAS EN EL LOGIN -> username + password
  login: string;
  password: string;
  //return = '';

	constructor(private authenticationService: AuthenticationService,
				private route: ActivatedRoute,
				private router: Router) {
	}

	//INICIALIZACIÓN DEL COMPONENTE -> recoge los parámetros de la url ?queryparams
	ngOnInit() {
		/*this.route.queryParams
		  .subscribe(params => this.return = params['return'] || '');*/
	}

	//COMPROBACIÓN DE LOGIN CORRECTO => REDIRECCION A OTRA URL
	logIn() {
    console.log("LOGIN.COMPONENT" + this.login);
    console.log("LOGIN.COMPONENT" + this.password);
		this.authenticationService.checkCredentials(this.login, this.password).subscribe((s) =>{
			this.authenticationService.logIn(this.login, this.password);
			this.router.navigateByUrl(/*this.return*/ `${environment.restApi}/home`);
		});
	}
}
