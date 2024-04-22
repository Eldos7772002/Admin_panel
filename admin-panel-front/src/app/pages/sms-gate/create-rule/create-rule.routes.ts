import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { CreateRuleComponent } from './create-rule.component';

export default [
    {
        path     : '',
        component: CreateRuleComponent,
        resolve  : {
        },
    },
] as Routes;
