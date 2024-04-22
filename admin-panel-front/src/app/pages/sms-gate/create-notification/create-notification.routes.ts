import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { CreateNotificationComponent } from './create-notification.component';

export default [
    {
        path     : '',
        component: CreateNotificationComponent,
        resolve  : {
        },
    },
] as Routes;
