import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rack } from '../models/rack';
import baseURL from './helper';

@Injectable({
  providedIn: 'root'
})
export class RackService {

  constructor(private httpClient: HttpClient) {}

  getRacksWarehouse(uuid: string): Observable<Rack[]> {
    return this.httpClient.get<Rack[]>(`${baseURL}/racks/warehouse/${uuid}`);
  }

  newRack(uuid: string, rack: Rack): Observable<Object> {
    return this.httpClient.post(`${baseURL}/racks/warehouse/${uuid}`, rack);
  }

  deleteRack(uuid: string): Observable<Object> {
    return this.httpClient.delete(`${baseURL}/racks/${uuid}`);
  }
}
