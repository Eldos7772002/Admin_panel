import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { SmsGateComponent } from './sms-gate.component';
import { SmsGateService } from '../../core/services/sms-gate.service';
import { CreateRuleComponent } from './create-rule/create-rule.component';

export default [
    {
        path     : '',
        component: SmsGateComponent,
        resolve  : {
            rulesData: () => inject(SmsGateService).getRulesData(),
            logData: () => inject(SmsGateService).getLogData()
        },
    },
    
    
] as Routes;
