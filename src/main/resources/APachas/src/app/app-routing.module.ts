import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {HomeComponent} from './components/home/home.component';
import {AuthGuard} from './guards/authGuard';

const routes: Routes = [
	{
		path: 'home', component: HomeComponent, canActivate: [AuthGuard]
	},
	{
		path: 'login', component: LoginComponent
	},
	{
		path: '', redirectTo: 'home', pathMatch: 'full'
	}
];

@NgModule({
	imports: [
		RouterModule.forRoot(routes, {
			useHash: false,
			anchorScrolling: 'enabled'
		})
	],
  	exports: [RouterModule]
})
export class AppRoutingModule { }