import { ApplicationConfig } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { APP_INITIALIZER, inject } from '@angular/core';
import {
    PreloadAllModules,
    provideRouter,
    withInMemoryScrolling,
    withPreloading,
} from '@angular/router';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { LuxonDateAdapter } from '@angular/material-luxon-adapter';
import { routes } from './app.routes';
import { provideTransloco, TranslocoService } from '@ngneat/transloco';
import { TranslocoHttpLoader } from 'app/shared/helpers/transloco.http-loader';
import { provideAnimations } from '@angular/platform-browser/animations';
import { mockApiServices } from 'app/shared/mockApi';
import { firstValueFrom } from 'rxjs';
import { provideFuse } from 'app/shared/config/fuse.provider';
import { provideIcons } from 'app/core/icons/icons.provider';

// import { firstValueFrom } from 'rxjs';

export const appConfig: ApplicationConfig = {
    providers: [
        provideAnimations(),
        provideHttpClient(),
        provideRouter(
            routes,
            withPreloading(PreloadAllModules),
            withInMemoryScrolling({ scrollPositionRestoration: 'enabled' })
        ),
        {
            provide : DateAdapter,
            useClass: LuxonDateAdapter,
        },
        {
            provide : MAT_DATE_FORMATS,
            useValue: {
                parse  : {
                    dateInput: 'D',
                },
                display: {
                    dateInput         : 'DDD',
                    monthYearLabel    : 'LLL yyyy',
                    dateA11yLabel     : 'DD',
                    monthYearA11yLabel: 'LLLL yyyy',
                },
            },
        },
         // Transloco Config
         provideTransloco({
            config: {
                availableLangs: [
                    {
                        id: 'en',
                        label: 'Eng',
                    },
                    {
                        id: 'ru',
                        label: 'Рус',
                    },
                    {
                        id: 'kz',
                        label: 'Қаз',
                    }, 
                ],
                defaultLang: 'ru',
                fallbackLang: 'ru',
                reRenderOnLangChange: true,
                prodMode: true,
            },
            loader: TranslocoHttpLoader,
        }),
        {
            // Preload the default language before the app starts to prevent empty/jumping content
            provide   : APP_INITIALIZER,
            useFactory: () =>
            {
                const translocoService = inject(TranslocoService);
                const defaultLang = translocoService.getDefaultLang();
                translocoService.setActiveLang(defaultLang);

                return () => firstValueFrom(translocoService.load(defaultLang));
            },
            multi     : true,
        },
        provideIcons(),
        provideFuse({
            mockApi: {
                delay   : 0,
                services: mockApiServices,
            },
            fuse   : {
                layout : 'modern',
                scheme : 'light',
                screens: {
                    sm: '600px',
                    md: '960px',
                    lg: '1280px',
                    xl: '1440px',
                },
                theme  : 'theme-default',
                themes : [
                    {
                        id  : 'theme-default',
                        name: 'Default',
                    },
                ]
            },
        }),
    ],

};
