module.exports = {
    base: '/',
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
                editLinkText: 'Edit this page on GitHub',
                lastUpdated: 'Last Updated',
                nav: require('./nav/en-US'),
                sidebar: {
                    '/docs/': getProjectBlogSidebar('BLOG SERVER')
                },
                sidebarDepth: 5
            },
            '/zh/': {
                label: '简体中文',
                selectText: '选择语言',
                ariaLabel: '选择语言',
                editLinkText: '在 GitHub 上编辑此页',
                lastUpdated: '上次更新',
                nav: require('./nav/zh-CN'),
                sidebar: {
                    '/zh/docs/': getProjectBlogSidebar('BLOG SERVER')
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

function getProjectBlogSidebar(groupName) {
    return [
        {
            title: groupName,
            collapsable: false,
            children: [
                '',
                'features',
                'install-and-deploy'
            ]
        }
    ]
}