import type { SidebarConfig } from '@vuepress/theme-default'

export const sidebarEn: SidebarConfig = {
  '/': [
    {
      text: 'Documentation',
      children: [
        '/index.md',
        '/config.md',
        '/commandline.md',
      ],
    },
    {
      text: 'Changelog',
      children: [
        '/changelog/0.2.md',
        '/changelog/0.1.md',
      ],
    },
    {
      text: 'Legal',
      children: [
        '/privacy.md',
        '/terms.md',
      ],
    },
  ],
};
