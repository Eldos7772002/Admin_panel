import { Injectable } from '@angular/core';
import { FuseMockApiService } from 'app/shared/services/mock-api/mock-api.service';
import { logData, rulesData } from './data';
import { cloneDeep } from 'lodash-es';

@Injectable({providedIn: 'root'})
export class SmsGateMockApi
{
    private _rulesData: any = rulesData;
    private _logData: any[] = logData;

    /**
     * Constructor
     */
    constructor(private _fuseMockApiService: FuseMockApiService)
    {
        // Register Mock API handlers
        this.registerHandlers();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Register Mock API handlers
     */
    registerHandlers(): void
    {
        // -----------------------------------------------------------------------------------------------------
        // @ Categories - GET
        // -----------------------------------------------------------------------------------------------------

        this._fuseMockApiService
            .onGet('api/projects/sms-gate/rules-data')
            .reply(() =>
            {
                // Clone the categories
                const rulesData = cloneDeep(this._rulesData);

                
                return [200, rulesData];
            });
        
        this._fuseMockApiService
            .onGet('api/projects/sms-gate/logs')
            .reply(() =>
            {
                // Clone the categories
                const logData = cloneDeep(this._logData);

                
                return [200, logData];
            });
    }
}
