import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private _http: HttpClient, private _authService: AuthService) {}

  private addTokenToHeaders(headers: HttpHeaders): HttpHeaders {
    const token = this._authService.accessToken;
    if (token) {
      return headers.append('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  get(url: string): Observable<any> {
    const headers = new HttpHeaders().append('Accept-Language', 'ru');
    const headersWithToken = this.addTokenToHeaders(headers);
    return this._http.get(url, { headers: headersWithToken });
  }

  post(url: string, body: any): Observable<any> {
    const headers = new HttpHeaders().append('Accept-Language', 'ru');
    const headersWithToken = this.addTokenToHeaders(headers);
    return this._http.post(url, body, { headers: headersWithToken });
  }

  delete(url: string): Observable<any> {
    const headers = new HttpHeaders().append('Accept-Language', 'ru');
    const headersWithToken = this.addTokenToHeaders(headers);
    return this._http.delete(url, { headers: headersWithToken });
  }
}
