<div align="center" width="100%">
    <img src="./magic-packet.png" width="150" />
</div>

# Magic Packet

这是一个用于发送幻包（Wake-on-LAN Magic Packet）的后端服务，使用 Java 编写，可以配合 Web 界面 [magic-packet-ui](https://github.com/yhxjs/magic-packet-ui) 一起食用。

## 功能简介

- 提供 HTTP API 接口，接收前端请求并发送幻包，实现唤醒局域网内的设备。
- 支持自定义目标设备的 MAC 地址、广播地址、端口。

## 项目启动

本项目使用了redis，因此需要先安装redis（后续将使用本地缓存去除redis）。

---

1. 克隆仓库并进入目录：

   ```bash
   git clone https://github.com/yhxjs/magic-packet.git
   cd magic-packet
   ```

2. 构建项目（需安装 Maven）：

   ```bash
   mvn clean package
   ```

3. 运行服务：

   ```bash
   java --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED -jar magic-packet-0.0.1.jar > magic-packet.log 2>&1
   ```

4. 服务端口为 `8081`，可通过配置文件修改。

> 可以在resources目录下新增static目录，将 Web 界面打包的文件放入，再进行后端项目的构建，可以直接访问 [http://localhost:8081](http://localhost:8081) 查看界面。

## 相关链接

- [Wake-on-LAN 介绍（维基百科）](https://zh.wikipedia.org/wiki/Wake-on-LAN)

---

如有问题，欢迎提交 Issue。