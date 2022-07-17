# 一个简单的 GitHub Android 官户端 Demo

## 重点演示
- GitHub 第三方登录，OAuth2 认证过程，跳到浏览器进行验证，然后跳回应用，获取 Access Token 等流程
- OkHttp 拦截器的使用，在请求的 Header 中自动设置 Token
- Retrofit 搭配 GitHub API 以及协程的使用
- 官方推荐架构的最佳实践，支持离线数据展示
- Jetpack 重要组件的使用：ViewModel, LiveData, Lifecycle, Room, View Binding 等


未完成所有页面，但不影响架构设计思想及API的演示。
进入 GitHub 认证时，请选译 Chrome 浏览器。
[测试 APK 下载](https://github.com/linsea/HiGitHub/blob/master/HiGitHub.apk?raw=true)
