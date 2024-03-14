# 多维 BI 微服务后端

## 技术栈：
- Spring Cloud Gateway
- Dubbo
- 使用 Docker 部署
- Nacos 作为注册中心

## 存在的Bug
- 当用户的 id 超过 19 位时，从后端传向前端精度会丢失（解决）
  - 修改数据库 id 的类型
  - 从后端传向前端时，将 id 转换位 String 类型
- 使用 dubbo 时出现空指针异常（解决）
  - 原因：由于一开始使用的 dubbo 版本为 1.x 的版本，在引入 @Reference 注解所需要的包时，引用错误
  - 解决方法：正确的引用 apach 的包
  - 更改版本 dubbo 升到 2.x 之后 @DubboReference 取代了 @Reference 注解
- 使用 docker 将 nacos 部署到服务器时，出现了内存爆满的情况（解决）
  - 限制 docker 的内存大小
- 前端将 excel 文件传到后端时，将文件过滤了
  - 可能的原因：网关将文件过滤了
  - ……
