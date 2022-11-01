import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { WarehouseFormComponent } from './components/warehouse-form/warehouse-form.component';
import { WarehouseListComponent } from './components/warehouse-list/warehouse-list.component';
import { LoginActivate } from './services/LoginActivate';

const routes: Routes = [
  {  path: '', redirectTo: 'login', pathMatch:'full'},
  {  path: 'login', component:LoginComponent},
  {  path: 'warehouses', component:WarehouseListComponent, canActivate:[LoginActivate]},
  {  path: 'new-warehouse', component:WarehouseFormComponent, canActivate:[LoginActivate]},
  {  path: 'update-warehouse/:id', component:WarehouseFormComponent, canActivate:[LoginActivate]},
  {  path: 'detail-warehouse/:id', component:WarehouseFormComponent, canActivate:[LoginActivate]},

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
