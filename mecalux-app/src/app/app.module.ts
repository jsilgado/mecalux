import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { WarehouseListComponent } from './components/warehouse-list/warehouse-list.component';
import { WarehouseFormComponent } from './components/warehouse-form/warehouse-form.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RackNewComponent } from './components/rack-new/rack-new.component';
import { LoginComponent } from './components/login/login.component';


import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatTableModule} from '@angular/material/table';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSnackBarModule} from '@angular/material/snack-bar';

import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { authInterceptorProviders } from './services/auth.interceptor';
import { LoginActivate } from './services/LoginActivate';



@NgModule({
  declarations: [
    AppComponent,
    WarehouseListComponent,
    NavbarComponent,
    RackNewComponent,
    LoginComponent,
    WarehouseFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatInputModule,
    MatTableModule,
    MatToolbarModule,
    MatIconModule,
    MatFormFieldModule,
    MatSelectModule,
    MatDialogModule,
    MatCardModule,
    MatDividerModule,
    MatTooltipModule,
    MatSnackBarModule,
    SweetAlert2Module
  ],
  providers: [authInterceptorProviders, LoginActivate],
  bootstrap: [AppComponent]
})
export class AppModule { }
