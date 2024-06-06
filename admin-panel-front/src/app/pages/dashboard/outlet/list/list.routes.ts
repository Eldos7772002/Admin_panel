import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
  Routes,
} from '@angular/router';
import {OutletListComponent} from "./list.component";

export default [
  {
    path: '',
    component: OutletListComponent,
  },
] as Routes;
