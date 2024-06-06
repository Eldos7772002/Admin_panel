/* eslint-disable */
import { FuseNavigationItem } from 'app/shared/components/navigation';

export const defaultNavigation: FuseNavigationItem[] = [];
export const compactNavigation: FuseNavigationItem[] = [];
export const futuristicNavigation: FuseNavigationItem[] = [];
export const horizontalNavigation: FuseNavigationItem[] = [
    {
        id      : 'dashboards',
        title   : 'Торговые центры',
        type    : 'basic',
        icon    : 'heroicons_outline:view-columns',
        link: '/projects'
    },
    {
      id      : 'outlets',
      title   : 'Бутики',
      type    : 'basic',
      icon    : 'heroicons_outline:view-columns',
      link: '/outlet/list'
    },
    {
      id      : 'dashboards',
      title   : 'Редактирование Контента',
      type    : 'group',
      icon    : 'heroicons_outline:adjustments-horizontal',
      children: [
        {
          id   : 'dashboard',
          title: 'Создание Торгового центра',
          type : 'basic',
          icon : 'heroicons_outline:chat-bubble-bottom-center-text',
          link : '/dashboard',
        },
        {
          id   : 'outlet',
          title: 'Создание бутика',
          type : 'basic',
          icon : 'heroicons_outline:chat-bubble-bottom-center-text',
          link : '/outlet',
        },
      ],
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
