import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
  Routes,
} from '@angular/router';
import { OutletComponent } from './outlet.component';

export default [
  {
    path: '',
    component: OutletComponent,
  },
] as Routes;
