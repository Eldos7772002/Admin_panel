import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateRuleModel } from '../models/create-rule.model';
import { ApiService } from './api.service';

@Injectable({providedIn: 'root'})
export class CreateRuleService
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

  createRule(createRuleComponent: CreateRuleModel): Observable<any> {
    return this.api.post('/gateway/sms-gate/api/v1/notice-push-rules', createRuleComponent);
  }

  getRuleInformation(id: string): Observable<any> {
    return this.api.get('/gateway/sms-gate/api/v1/notice-push-rules/' + id);
  }
}