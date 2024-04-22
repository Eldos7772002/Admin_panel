import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
  Routes,
} from '@angular/router';
import { MainComponent } from './main.component';
import { MainService } from '../../core/services/main.service';
import { MainListComponent } from './list/list.component';
import { catchError, throwError } from 'rxjs';
import { SmsGateComponent } from '../sms-gate/sms-gate.component';

export default [
  {
    path: '',
    component: MainComponent,
    resolve: {
      categories: () => inject(MainService).getCategories(),
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        component: MainListComponent,
        resolve: {
          courses: () => inject(MainService).getCourses(),
        },
      },
    ],
  },
] as Routes;
