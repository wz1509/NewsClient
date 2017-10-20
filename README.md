# NewsClient

[1]:https://github.com/ReactiveX/RxJava
[2]:https://github.com/ReactiveX/RxAndroid
[3]:https://github.com/square/retrofit
[4]:https://github.com/google/dagger
[5]:https://github.com/trello/RxLifecycle
[6]:https://github.com/bumptech/glide
[7]:https://github.com/chrisbanes/PhotoView
[8]:https://github.com/JakeWharton/butterknife

## 前言
一款 Material Design 风格的新闻App，采用 Mvp + RxJava + Retrofit + Dagger2 设计模式，整体风格为  NavigationView + Fragment 开发。

<img src="https://github.com/wz1509/NewsClient/blob/master/image/client_%20navigation_view.jpg?raw=true" width="200" hight="356" alt="navigation_view.jpg" title="navigation_view.jpg" />

## 项目截图
<img src="https://github.com/wz1509/NewsClient/blob/master/image/news_image.png?raw=true" width="200" hight="356" alt="news_image.png" title="news_image.png" />  <img src="https://github.com/wz1509/NewsClient/blob/master/image/photo_image.png?raw=true" width="200" hight="356" alt="photo_image.png" title="photo_image.png" /> <img src="https://github.com/wz1509/NewsClient/blob/master/image/gank_io.png?raw=true" width="200" hight="356" alt="gank_io.png" title="gank_io.png" />  <img src="https://github.com/wz1509/NewsClient/blob/master/image/gank_io_girl.png?raw=true" width="200" hight="356" alt="gank_io_girl.png" title="gank_io_girl.png" />

<img src="https://github.com/wz1509/NewsClient/blob/master/image/news_image.gif?raw=true" width="200" hight="356" alt="news_image.png" title="news_image.png" />  <img src="https://github.com/wz1509/NewsClient/blob/master/image/photo_image.gif?raw=true" width="200" hight="356" alt="photo_image.gif" title="photo_image.gif" /> <img src="https://github.com/wz1509/NewsClient/blob/master/image/gank_io.gif?raw=true" width="200" hight="356" alt="gank_io.gif" title="gank_io.gif" />  <img src="https://github.com/wz1509/NewsClient/blob/master/image/gank_io_girl.gif?raw=true" width="200" hight="356" alt="gank_io_girl.gif" title="gank_io_girl.gif" />

## 使用到的开源库
- [RxJava][1]        JVM上的响应式扩展，一个实现异步操作的库
- [RxAndroid][2]   Android上为RxJava提供Joins操作
- [Retrofit][3]        类型安全的Http客户端,配合RxJava食用更佳
- [Dagger2][4]      一个Android和Java快速依赖注入库
- [RxLifecycle][5] 防止RxJava中subscription导致内存泄漏
- [Glide][6]           Google出品的图片加载和缓存的库,可加载动图
- [PhotoView][7]  可根据手势进行缩放的图像库
- [Butterknife][8] 将Android视图和回调方法绑定到字段和方法上,JakeWharton大神的力作

项目中的 RecyclerView 下拉刷新是用 SwipeRefreshLayout，加载更多新闻列表我是用原生写的，gank.io 我封装了一个 BaseRecyclerViewAdapter，可能封装的不太好😅，但是稍微看一下基本上可以自己写一个通用的。

## 数据来源
- [天行数据Api](https://www.tianapi.com/)
- [gank.io](http://gank.io/api)

## 学习目标
- MVP模式的运用
- Dagger2在MVP中基础运用
- Material Design 应用

## 最后
不知道写啥了...😓😓😓


