import type { HeadConfig } from '@vuepress/core';

export const head: HeadConfig[] = [
  [
    'link',
    {
      rel: 'icon',
      type: 'image/png',
      sizes: '16x16',
      href: `/favicon-16.png`,
    },
  ],
  [
    'link',
    {
      rel: 'icon',
      type: 'image/png',
      sizes: '32x32',
      href: `/favicon-32.png`,
    },
  ],
  ['meta', { name: 'theme-color', content: '#936edb' }],
  ['meta', { name: 'og:title', content: 'zProtect Documentation' }],
  ['meta', { name: 'og:type', content: 'website' }],
  ['meta', { name: 'og:image', content: '/og.png' }],
  ['meta', { name: 'og:url', content: 'https://docs.zprotect.dev/' }],
]