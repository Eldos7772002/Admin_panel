import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
  Routes,
} from '@angular/router';
import { DashboardComponent } from './dashboard.component';

export default [
  {
    path: '',
    component: DashboardComponent,
  },
] as Routes;
