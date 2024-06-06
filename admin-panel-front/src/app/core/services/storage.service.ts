import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private apiUrl = 'http://localhost:8082/cabinet/dp-core-storage/api/v1/files/upload';

  constructor(private http: HttpClient, private api: ApiService) {}

  uploadImage(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('code', 'dp.le.contract');

    return this.http.post<{ downloadUrl: string }>(this.apiUrl, formData);
  }
}
