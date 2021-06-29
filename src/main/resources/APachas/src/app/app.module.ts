import {ErrorHandler, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NotificationModule} from './modules/notification/notification.module';
import {SimpleNotificationsModule} from 'angular2-notifications';
import {ErrorNotificationHandler} from './modules/notification/handlers/error-notification.handler';
import {LoginComponent} from './components/login/login.component';
import {AuthenticationInterceptor} from './helpers/authentication.interceptor';
import {HomeComponent} from './components/home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {RegisterComponent} from "./components/register/register.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    NotificationModule,
    BrowserAnimationsModule,
    SimpleNotificationsModule.forRoot({
      timeOut: 10000,
      preventDuplicates: true,
      pauseOnHover: true,
      clickToClose: true
    })
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
