import { defaultTheme, defineUserConfig } from 'vuepress';
import { searchPlugin } from '@vuepress/plugin-search';
import { shikiPlugin } from '@vuepress/plugin-shiki';
import markdownItFootnote from 'markdown-it-footnote';
import { navbarEn } from './config/navbar/en';
import { sidebarEn } from './config/sidebar/en';
import { head } from './config/head';

export default defineUserConfig({
  base: '/',
  head,
  locales: {
    '/': {
      lang: 'en-US',
      title: 'zProtect Documentation',
      description: 'Documentation for all things zProtect.',
    }
  },
  theme: defaultTheme({
    contributors: false,
    docsRepo: 'https://github.com/zProtect-Development/Documentation',
    docsBranch: 'main',
    docsDir: 'docs',
    editLink: true,
    editLinkPattern: ':repo/edit/:branch/:path',
    lastUpdated: true,
    logo: '/logo.png',
    locales: {
      '/': {
        sidebar: sidebarEn,
        navbar: navbarEn,
      },
    }
  }),
  plugins: [
    searchPlugin({
    }),
    shikiPlugin({
      theme: 'dark-plus',
    }),
  ],
  extendsMarkdown: (md) => {
    md.use(markdownItFootnote)
  }
});
