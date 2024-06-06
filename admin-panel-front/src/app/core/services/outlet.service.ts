import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {map, Observable} from 'rxjs';
import { catchError } from 'rxjs/operators';
import {Mall} from "../models/main.model";
import {ApiService} from "./api.service";
import {Outlet} from "../models/outlet.model";

@Injectable({
  providedIn: 'root'
})
export class OutletsService {

  private apiUrl = '/gateway/websocket/api/v2/outlets';

  constructor(private http: HttpClient, private api: ApiService) {}

  // Метод для получения данных с API
  getOutlets(): Observable<Outlet[]> {
    return this.api.get(this.apiUrl)
  }

  createOutlets(mallData: any): Observable<Outlet> {
    return this.api.post(this.apiUrl, mallData);
  }
}
