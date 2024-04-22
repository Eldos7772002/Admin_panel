import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { CreateNotificationModel } from '../models/create-notification.model';

@Injectable({providedIn: 'root'})
export class CreateNotificationService
{
  /**
   * Constructor
   */
  constructor(private api: ApiService)
  {
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Accessors
  // -----------------------------------------------------------------------------------------------------

  
  createNotification(notification: CreateNotificationModel): Observable<any>{
    return this.api.post('/gateway/sms-gate/api/v1/notifications', notification)
  }

  getNoteInformation(id: string): Observable<any> {
    return this.api.get('/gateway/sms-gate/api/v1/notifications/' + id);
  }
}