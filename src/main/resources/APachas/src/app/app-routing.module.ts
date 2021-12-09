import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {HomeComponent} from './components/home/home.component';
import {AuthGuard} from './guards/authGuard';
import {RegisterComponent} from "./components/register/register.component";
import {ListUsersComponent} from "./components/users/listUsers/listUsers.component";
import {CreateGroupComponent} from "./components/groups/createGroup/createGroup.component";
import {ListGroupsComponent} from "./components/groups/listGroups/listGroups.component";
import {DetailGroupComponent} from "./components/groups/detailGroup/detailGroup.component";
import {DetailUserComponent} from "./components/users/detailUser/detailUser.component";
import {MessageConfirmComponent} from "./components/confirm/messageConfirm.component";
import {ListEventsComponent} from "./components/events/listEvents/listEvents.component";
import {DetailEventComponent} from "./components/events/detailEvent/detailEvent.component";
import {CreateEventComponent} from "./components/events/createEvent/createEvent.component";

const routes: Routes = [
	{
		path: 'home', component: HomeComponent/*, canActivate: [AuthGuard]*/
	},
	{
		path: 'login', component: LoginComponent
	},
  	{
    	path: 'register', component: RegisterComponent
  	},
	{
		path: 'listUsers', component: ListUsersComponent, canActivate: [AuthGuard]
	},
	{
		path: 'createGroup', component: CreateGroupComponent, canActivate: [AuthGuard]
	},
	{
		path: 'listGroups', component: ListGroupsComponent, canActivate: [AuthGuard]
	},
	{
		path: 'detailGroup', component: DetailGroupComponent, canActivate: [AuthGuard]
	},
	{
		path: 'detailUser', component: DetailUserComponent, canActivate: [AuthGuard]
	},
	{
		path: 'messageConfirm', component: MessageConfirmComponent, canActivate: [AuthGuard]
	},
	{
		path: 'listEvents', component: ListEventsComponent, canActivate: [AuthGuard]
	},
	{
		path: 'detailEvent', component: DetailEventComponent, canActivate: [AuthGuard]
	},
	{
		path: 'createEvent', component: CreateEventComponent, canActivate: [AuthGuard]
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
