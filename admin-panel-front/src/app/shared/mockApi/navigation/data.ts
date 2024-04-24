/* eslint-disable */
import { FuseNavigationItem } from 'app/shared/components/navigation';

export const defaultNavigation: FuseNavigationItem[] = [];
export const compactNavigation: FuseNavigationItem[] = [];
export const futuristicNavigation: FuseNavigationItem[] = [];
export const horizontalNavigation: FuseNavigationItem[] = [
    {
        id      : 'dashboards',
        title   : 'Панель управления',
        type    : 'basic',
        icon    : 'heroicons_outline:view-columns',
        link: '/projects'
    },
    {
        id      : 'dashboards',
        title   : 'Торговые центры',
        type    : 'group',
        icon    : 'heroicons_outline:document',
        children: [
            {
                id   : 'dashboards.project',
                title: 'Forum',
                type : 'basic',
                icon : 'heroicons_outline:chat-bubble-bottom-center-text',
                link : '/projects/forum',
            },
            {
                id   : 'dashboards.project',
                title: 'Moskva Metropolian',
                type : 'basic',
                icon : 'heroicons_outline:chat-bubble-bottom-center-text',
                link : '/projects/moskva',
            },
            {
                id   : 'dashboards.project',
                title: 'Dostyk Plaza',
                type : 'basic',
                icon : 'heroicons_outline:chat-bubble-bottom-center-text',
                link : '/projects/dostyk-plaza',
            },
        ],
    },
];
