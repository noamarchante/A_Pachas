import {ErrorHandler, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NotificationModule} from './modules/notification/notification.module';
import {SimpleNotificationsModule} from 'angular2-notifications';
import {ErrorNotificationHandler} from './modules/notification/handlers/error-notification.handler';
import {LoginComponent} from './components/authUser/login/login.component';
import {AuthenticationInterceptor} from './helpers/authentication.interceptor';
import {HomeComponent} from './components/home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {RegisterComponent} from "./components/authUser/register/register.component";
import {ListUsersComponent} from "./components/users/listUsers/listUsers.component";
import {CreateGroupComponent} from "./components/groups/createGroup/createGroup.component";
import {ListGroupsComponent} from "./components/groups/listGroups/listGroups.component";
import { NgSelectModule } from '@ng-select/ng-select';
import {DetailGroupComponent} from "./components/groups/detailGroup/detailGroup.component";
import {DetailUserComponent} from "./components/users/detailUser/detailUser.component";
import {MessageConfirmComponent} from "./components/confirm/messageConfirm.component";
import {ListEventsComponent} from "./components/events/listEvents/listEvents.component";
import {DetailEventComponent} from "./components/events/detailEvent/detailEvent.component";
import {CreateEventComponent} from "./components/events/createEvent/createEvent.component";
import {Daterangepicker} from "ng2-daterangepicker";
import {ListProductsComponent} from "./components/products/listProducts/listProducts.component";
import {CreateProductComponent} from "./components/products/createProduct/createProduct.component";
import {DetailProductComponent} from "./components/products/detailProduct/detailProduct.component";
import {CreateUserEventExpenseComponent} from "./components/products/createUserEventExpense/createUserEventExpense.component";
import {DetailProfileComponent} from "./components/authUser/profile/detailProfile/detailProfile.component";
import {ListTransactionsComponent} from "./components/transactions/listTransactions.component";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ListUsersComponent,
    CreateGroupComponent,
    ListGroupsComponent,
    DetailGroupComponent,
    DetailUserComponent,
    MessageConfirmComponent,
    ListEventsComponent,
    DetailEventComponent,
    CreateEventComponent,
    CreateProductComponent,
    CreateUserEventExpenseComponent,
    ListProductsComponent,
    DetailProductComponent,
    ListTransactionsComponent,
    DetailProfileComponent,
    HomeComponent
  ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        FormsModule,
        NgSelectModule,
        HttpClientModule,
        NotificationModule,
        BrowserAnimationsModule,
        SimpleNotificationsModule.forRoot({
            timeOut: 10000,
            preventDuplicates: true,
            pauseOnHover: true,
            clickToClose: true
        }),
        Daterangepicker
    ],

  providers: [
    {
      provide: ErrorHandler,
      useClass: ErrorNotificationHandler
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor, multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
