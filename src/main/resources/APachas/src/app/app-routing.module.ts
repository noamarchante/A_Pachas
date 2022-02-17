import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/authUser/login/login.component';
import {HomeComponent} from './components/home/home.component';
import {AuthGuard} from './guards/authGuard';
import {RegisterComponent} from "./components/authUser/register/register.component";
import {ListUsersComponent} from "./components/users/listUsers/listUsers.component";
import {CreateGroupComponent} from "./components/groups/createGroup/createGroup.component";
import {ListGroupsComponent} from "./components/groups/listGroups/listGroups.component";
import {DetailGroupComponent} from "./components/groups/detailGroup/detailGroup.component";
import {DetailUserComponent} from "./components/users/detailUser/detailUser.component";
import {MessageConfirmComponent} from "./components/confirm/messageConfirm.component";
import {ListEventsComponent} from "./components/events/listEvents/listEvents.component";
import {DetailEventComponent} from "./components/events/detailEvent/detailEvent.component";
import {CreateEventComponent} from "./components/events/createEvent/createEvent.component";
import {CreateProductComponent} from "./components/products/createProduct/createProduct.component";
import {ListProductsComponent} from "./components/products/listProducts/listProducts.component";
import {DetailProductComponent} from "./components/products/detailProduct/detailProduct.component";
import {CreateUserEventExpenseComponent} from "./components/products/createUserEventExpense/createUserEventExpense.component";
import {DetailProfileComponent} from "./components/authUser/profile/detailProfile/detailProfile.component";
import {ListTransactionsComponent} from "./components/transactions/listTransactions.component";

const routes: Routes = [
	{
		path: 'home', component: HomeComponent
	},
	{
		path: 'login', component: LoginComponent
	},
  	{
    	path: 'register', component: RegisterComponent
  	},
	{
		path: 'users', component: ListUsersComponent, canActivate: [AuthGuard]
	},
	{
		path: 'createGroup', component: CreateGroupComponent, canActivate: [AuthGuard]
	},
	{
		path: 'groups', component: ListGroupsComponent, canActivate: [AuthGuard]
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
		path: 'events', component: ListEventsComponent, canActivate: [AuthGuard]
	},
	{
		path: 'detailEvent', component: DetailEventComponent, canActivate: [AuthGuard]
	},
	{
		path: 'createEvent', component: CreateEventComponent, canActivate: [AuthGuard]
	},
	{
		path: 'createProduct', component: CreateProductComponent, canActivate: [AuthGuard]
	},
	{
		path: 'products', component: ListProductsComponent, canActivate: [AuthGuard]
	},
	{
		path: 'detailProduct', component: DetailProductComponent, canActivate: [AuthGuard]
	},
	{
		path: 'createUserEventExpense', component: CreateUserEventExpenseComponent, canActivate: [AuthGuard]
	},
	{
		path: 'transactions', component: ListTransactionsComponent, canActivate: [AuthGuard]
	},
	{
		path: 'profile', component: DetailProfileComponent, canActivate: [AuthGuard]
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
