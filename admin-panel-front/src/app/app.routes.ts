import { Route } from '@angular/router';
import { initialDataResolver } from './app.resolvers';
import { AuthGuard } from './core/auth/guards/auth.guard';
import { NoAuthGuard } from './core/auth/guards/noAuth.guard';
import { LayoutComponent } from './core/layout/layout.component';

// @formatter:off
/* eslint-disable max-len */
/* eslint-disable @typescript-eslint/explicit-function-return-type */

export const routes: Route[] = [
    {path: '', pathMatch : 'full', redirectTo: 'projects'},
    {path: 'signed-in-redirect', pathMatch : 'full', redirectTo: 'projects'},

    {
        path: '',
        canActivate: [NoAuthGuard],
        canActivateChild: [NoAuthGuard],
        component: LayoutComponent,
        data: {
            layout: 'empty'
        },
        children: [
            {path: 'confirmation-required', loadChildren: () => import('../app/pages/auth/confirmation-required/confirmation-required.routes')},
            {path: 'forgot-password', loadChildren: () => import('../app/pages/auth/forgot-password/forgot-password.routes')},
            {path: 'reset-password', loadChildren: () => import('../app/pages/auth/reset-password/reset-password.routes')},
            {path: 'sign-in', loadChildren: () => import('../app/pages/auth/sign-in/sign-in.routes')},
            {path: 'sign-up', loadChildren: () => import('../app/pages/auth/sign-up/sign-up.routes')}
        ]
    },

    // Auth routes for authenticated users
    {
        path: '',
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        component: LayoutComponent,
        data: {
            layout: 'empty'
        },
        children: [
            {path: 'sign-out', loadChildren: () => import('../app/pages/auth/sign-out/sign-out.routes')},
            {path: 'unlock-session', loadChildren: () => import('../app/pages/auth/unlock-session/unlock-session.routes')}
        ]
    },

    {
        path: '',
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        component: LayoutComponent,
        resolve: {
            initialData: initialDataResolver
        },
        children: [
            {path: 'projects', loadChildren: () => import('../app/pages/main/main.routes')},
            {path: 'projects', children: [
                {path: 'forum', loadChildren: () => import('../app/pages/sms-gate/sms-gate.routes')},
                {path: 'forum/create-rule/:id', loadChildren: () => import('./pages/sms-gate/create-rule/create-rule.routes')},
                {path: 'forum/create-notification/:id', loadChildren: () => import('./pages/sms-gate/create-notification/create-notification.routes')}
            ]},
            {path: 'profile', loadChildren: () => import('../app/pages/profile/profile.routes')},
          {path: 'dashboard', loadChildren: () => import('../app/pages/dashboard/dashboard.routes')},
          {path: 'outlet', loadChildren: () => import('../app/pages/dashboard/outlet/outlet.routes')},
          {path: 'outlet/list', loadChildren: () => import('../app/pages/dashboard/outlet/list/list.routes')},
        ]
    }
];
