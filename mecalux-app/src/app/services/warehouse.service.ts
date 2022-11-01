import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Warehouse } from '../models/warehouse';
import baseURL from './helper';

@Injectable({
  providedIn: 'root',
})
export class WarehouseService {



  constructor(private httpClient: HttpClient) {}

  getLstWarehouses(): Observable<Warehouse[]> {
    return this.httpClient.get<Warehouse[]>(`${baseURL}/warehouses`);
  }

  newWarehouse(warehouse: Warehouse): Observable<Object> {
    return this.httpClient.post(`${baseURL}/warehouses`, warehouse);
  }

  updateWarehouse(uuid: string, warehouse: Warehouse): Observable<Object> {
    return this.httpClient.put(`${baseURL}/warehouses/${uuid}`, warehouse);
  }

  getWarehouse(uuid: string): Observable<Warehouse> {
    return this.httpClient.get<Warehouse>(`${baseURL}/warehouses/${uuid}`);
  }

  deleteWarehouse(uuid: string): Observable<Object> {
    return this.httpClient.delete(`${baseURL}/warehouses/${uuid}`);
  }
}
