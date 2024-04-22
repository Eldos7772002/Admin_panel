import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, tap } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({ providedIn: 'root' })
export class SmsGateService {

  /**
   * Constructor
   */
  constructor(private _httpClient: HttpClient, private api: ApiService) 
  {

  }
  
  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Get data
   */

  getNotificationsInfo(): Observable<any> {
    return this.api.get('/gateway/sms-gate/api/v1/notifications');
  }

  getRulesData(): Observable<any> {
    return this.api.get('/gateway/sms-gate/api/v1/notice-push-rules')
  }

  getLogData(): Observable<any> {
    return this._httpClient.get<any[]>('api/projects/sms-gate/logs')
  }

  /**
   * Delete data
   */

  deleteRule(id: string): Observable<any> {
    return this.api.delete('/gateway/sms-gate/api/v1/notice-push-rules/' + id);
  }

  deleteNotification(id: string): Observable<any> {
    return this.api.delete('/gateway/sms-gate/api/v1/notifications/' + id)
  }

}
