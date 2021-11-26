module.exports = {
    base: '/blog-server/',
    locales: {
        // en-US
        '/': {
            lang: 'en-US',
            title: 'BLOG-SERVER',
            description: 'Docs for Project: BLOG-SERVER by fzcoder'
        },
        // zh-CN
        '/zh/': {
            lang: 'zh-CN',
            title: 'BLOG-SERVER',
            description: '这是fzcoder开源的BLOG项目服务端文档'
        }
    },
    // default theme
    themeConfig: {
        // logo: '',
        locales: {
            '/': {
                label: 'English',
                selectText: 'Languages',
                ariaLabel: 'Select language',
                search: true,
                searchMaxSuggestions: 10,
                repo: 'fzcoder/blog-server',
                repoLabel: 'Contribute',
                editLinks: true,
                editLinkText: 'Edit this page on GitHub',
                lastUpdated: 'Last Updated',
                nav: require('./nav/en-US'),
                sidebar: {
                    '/v1.x/': getV1xSidebar('Guide', 'API'),
                    '/': getCurrentVersionSidebar('Guide', 'API')
                },
                sidebarDepth: 5
            },
            '/zh/': {
                label: '简体中文',
                selectText: '选择语言',
                ariaLabel: '选择语言',
                search: true,
                searchMaxSuggestions: 10,
                repo: 'fzcoder/blog-server',
                repoLabel: '查看源码',
                editLinks: true,
                editLinkText: '在 GitHub 上编辑此页',
                lastUpdated: '上次更新',
                nav: require('./nav/zh-CN'),
                sidebar: {
                    '/zh/v1.x/': getV1xSidebar('Guide', 'API'),
                    '/zh/': getCurrentVersionSidebar('指南', 'API')
                },
                sidebarDepth: 5
            }
        }
    },
    markdown: {},
    plugins: [
        '@vuepress/active-header-links',
        '@vuepress/back-to-top'
    ]
}

function getCurrentVersionSidebar(groupA, groupB) {
    return [
        {
            title: groupA,
            collapsable: false,
            children: [
                '',
                'features',
                'install-and-deploy'
            ]
        },
        {
            title: groupB,
            collapsable: false,
            children: [
                'api',
            ]
        }
    ]
}

function getV1xSidebar(groupA, groupB) {
    return [
        {
            title: groupA,
            collapsable: false,
            children: [
                ''
            ]
        }
    ]
}