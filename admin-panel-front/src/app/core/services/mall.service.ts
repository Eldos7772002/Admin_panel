import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {map, Observable} from 'rxjs';
import { catchError } from 'rxjs/operators';
import {Mall} from "../models/main.model";
import {ApiService} from "./api.service";

@Injectable({
  providedIn: 'root'
})
export class MallsService {

  private apiUrl = '/gateway/websocket/api/v2/malls';

  constructor(private http: HttpClient, private api: ApiService) {}

  // Метод для получения данных с API
  getMalls(): Observable<Mall[]> {
    return this.api.get(this.apiUrl)
  }

  createMall(mallData: any): Observable<Mall> {
    return this.api.post(this.apiUrl, mallData);
  }
}
