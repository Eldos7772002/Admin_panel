import { Injectable } from '@angular/core';
import { FuseMockApiService } from 'app/shared/services/mock-api/mock-api.service';
import { project as projectData } from 'app/shared/mockApi/project/data';
import { cloneDeep } from 'lodash-es';

@Injectable({providedIn: 'root'})
export class ProjectMockApi
{
    private _project: any = projectData;

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
        // @ Sales - GET
        // -----------------------------------------------------------------------------------------------------
        this._fuseMockApiService
            .onGet('api/dashboards/project')
            .reply(() => [200, cloneDeep(this._project)]);
    }
}
