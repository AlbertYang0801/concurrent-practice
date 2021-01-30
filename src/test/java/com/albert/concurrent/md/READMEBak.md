

# start from scratch
从零开始服务端代码

## 重要说明
本项目是以学成在线项目为基础进行扩展的，作为本人的软件工程毕设项目，完成度很高。近期准备完善文档和项目优化，有需要可以留言，不喜勿喷。

```
         _______________________________________________        
        |   _      __        __                         |       
________|  | | /| / / ___   / / ____ ___   __ _  ___    |_______
\       |  | |/ |/ / / -_) / / / __// _ \ /  ' \/ -_)   |      /
 \      |  |__/|__/  \__/ /_/  \__/ \___//_/_/_/\__/    |     / 
 /      |_______________________________________________|     \ 
/__________)                                        (__________\
```
* [sfs 介绍](#sfs-介绍)
  * [重要说明](#重要说明)
  * [项目介绍](#项目介绍)
  * [总体设计](#总体设计)
  * [技术架构](#技术架构)
  * [项目环境](#项目环境)
  * [项目启动总结](#项目启动总结)
    * [前端启动:](#前端启动)
    * [后端启动:](#后端启动)
    * [启动虚拟机：](#启动虚拟机：)
    * [ElasticSearch启动:](#elasticsearch启动)
    * [Nginx启动](#nginx启动)
  * [功能模块](#功能模块)
    * [门户网站](#门户网站)
    * [搜索课程](#搜索课程)
    * [学习中心](#学习中心)
    * [用户中心](#用户中心)
    * [教学管理中心](#教学管理中心)
      * [课程管理](#课程管理)
        * [1. 新增课程基本信息](#1-新增课程基本信息)
        * [2. 管理课程](#2-管理课程)
        * [3. 课程图片管理](#3-课程图片管理)
        * [课程章节管理](#课程章节管理)
        * [课程发布](#课程发布)
      * [媒资管理](#媒资管理)
        * [上传文件](#上传文件)
        * [我的媒资](#我的媒资)
    * [系统管理中心](#系统管理中心)
      * [CMS内容管理模块](#cms内容管理模块)
      * [系统管理模块](#系统管理模块)



## 项目介绍
>本项目全名为start from scratch（简称SFS），中文名为从零开始。SFS向个人提供在线教育,为平台提供教学服务，个人用户可以根据自己选择进行学习，老师和学生可以通过平台完成整个教学和学习的过程，市场上类似的平台有：沪江网校等， SFS包括门户、学习中心、教学管理中心、系统管理等功能模块。包含登陆认证、权限管理、课程搜索等技术模块。而且技术上跟进主流。
本文主要描述的是基于微服务的SFS在线学习网站的教学管理中心和系统管理中心两个系统后台的设计与实现。用户进入教学管理中心和系统管理中心都需要进行登录，能登录的用户身份包括教学管理员、系统管理员和超级管理员。教学管理中心主要是对网站的课程信息和课程媒资文件等进行管理，教学管理中心的参与者是教学管理员。系统管理中心主要是对网站页面进行内容管理，和对用户信息进行管理，系统管理中心后台主要用户是系统管理员。而超级管理员拥有着两个系统的所有权限，能进入系统管理中心，也能进入教学管理中心。



## 总体设计
![总体设计图](https://images.gitee.com/uploads/images/2021/0130/132614_f970eebb_5573516.png "总统设计图.png")


## 技术架构

本项目采用前后端分离模式，通过nginx代理转发。
- 服务端：使用Spring Boot构建、采用Spring Cloud微服务架构、Nginx作为代理服务器、负载均衡等。
- 持久层：数据库使用MySQL、MongoDB、Redis等。
- 数据访问层：使用Spring Data JPA 、Mybatis、Spring Data Mongodb、、Spring Data Redis 、RabbitMQ、HLS、搜索功能使用ElasticSearch、图片服务器使用FastDFS、文件服务器使用GridFS等。
- 业务层：Spring IOC、Aop事务控制、Spring Task任务调度、Feign、Ribbon等。
- 控制层：Spring MVC、RestTemplate、Spring Security Oauth2+JWT等。
- 微服务：Eureka、Zuul、Hystrix、Spring Cloud Conﬁg等。


## 项目环境

- JDK-1.8
- SpringBoot-2.0.1
- Mysql-5.1
- MongoDB
- redis-3.0.4
- RabbitMQ-3.7.18
- CentOS-7.6
- FastDFS
- ElasticSearch
- nginx-1.14.0
- FFmpeg-2018

## 项目启动总结
>项目采用前后端分类架构，后端采用微服务架构，前端使用vue+webpack，启动不同的模块。
项目需要启动的其他程序有虚拟机(安装了图片服务器FastDFS，设置了开机自启动)、ElasticSearch相关程序、Nginx。

### 前端启动:
1. 启动动态门户-搜索工程：sfs-ui-pc-teach-dev
    - 端口:10000
2. 启动系统管理中心工程：sfs-ui-pc-sysmanage-dev 
    - 端口:11000
3. 启动教学管理中心工程：sfs-ui-pc-teach-dev
    - 端口:12000
4. 启动学习中心工程：sfs-ui-pc-teach-dev
    - 端口:13000

### 后端启动:
1. 启动注册中心工程：sfs-govern-center
    - 端口:50101
2. 启动cms服务工程：sfs-service-manage-cms
    - 端口:31001
3. 启动cms消费者工程：sfs-service-manage-cms-client
    - 端口:31000
4. 启动文件系统工程：sfs-service-base-filesystem
    - 端口:22100
5. 启动课程管理工程：sfs-service-manage-course
    - 端口:31200
6. 启动搜索工程：sfs-service-search
    - 端口:40100
7. 启动媒资工程：sfs-service-manage-media
    - 端口:31400
8. 启动媒资处理工程：sfs-service-manage-media-processor
    - 端口:31450

### 启动虚拟机：
>虚拟机启CentOS 7.6_SFS里安装FastDFS和Redis，并设置开机自启动。注意下方信息为个人安装虚拟机信息!

- 虚拟机IP:192.168.126.110
- 用户名:root	
- 密码:123456


FastDFS安装和设置开机自启动参考博客地址：[FastDFS图片服务器安装步骤及遇到的问题博客目录](https://blog.csdn.net/qq_40389276/category_9515622.html)


### ElasticSearch启动:
1.启动ElasticSearch
2.启动head插件
3.启动logstash，输入命令: 
```
logstash.bat -f ..\config\mysql.conf
```

可参考：[ES启动总结](https://gitee.com/zztiyjw/sfsProject/wikis/ES%E5%90%AF%E5%8A%A8%E6%80%BB%E7%BB%93?sort_id=3482586)

### Nginx启动
>nginx安装在windows系统，设置自启动。

配置文件内容可见：[nginx配置文件内容](https://gitee.com/zztiyjw/sfsProject/wikis/%E9%A1%B9%E7%9B%AEnginx%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E5%86%85%E5%AE%B9?sort_id=3482576)



## 功能模块

### 门户网站
### 搜索课程
### 学习中心
### 用户中心






### 教学管理中心
---
**概述**

教学管理中心的核心是对课程资源和视频资源进行后台管理，系统主要使用者是教学管理员。教学管理员可以是网站的合作教师或者是教育机构的教师，是教学管理中心最重要的参与者。

**模块介绍**
>教学管理中心主要包含课程管理模块和媒资管理两个模块。课程管理模块主要是对整个学习网站包含的课程信息进行管理，该模块主要包含课程基本信息管理、课程图片信息管理、课程章节管理和课程发布管理。而媒资管理主要是对网站所有的媒资文件进行管理，包含上传媒资文件和媒资文件信息管理。

![教学管理用例图](https://images.gitee.com/uploads/images/2021/0130/140030_df4bdc0b_5573516.png "教学管理用例图.png")

#### 课程管理

>课程管理模块是教学管理中心的一个子模块，对网站拥有的所有课程进行管理。包括新增课程基本信息、管理课程、课程图片、课程章节、课程预览和课程发布等操作。

![我的课程](https://images.gitee.com/uploads/images/2021/0130/204556_7713305a_5573516.png "1.png")

**模块地址**

[sfs-service-manage-course](sfs-service-manage-course)

##### 1. 新增课程基本信息
新增课程基本信息可设置课程名称、课程等级、等展示信息，还可设置课程分类，用于课程搜索。

![新增课程](https://images.gitee.com/uploads/images/2021/0130/205008_877ccd54_5573516.png "新增课程.png")

代码见：[课程管理相关代码Controller](src\main\java\com\sfs\managecourse\controller\CourseController.java) 

##### 2. 管理课程

可修改课程名称、课程等级、等展示信息，还可设置课程分类，用于课程搜索。

![管理课程基本信息](https://images.gitee.com/uploads/images/2021/0130/205017_84c7f77b_5573516.png "新增课程基本信息.png")

代码见：[课程管理相关代码Controller](src\main\java\com\sfs\managecourse\controller\CourseController.java) 


##### 3. 课程图片管理

> 课程图片的管理使用了FastDFS图片服务器，包含图片上传和图片删除操作。图片上传是将图片文件上传到图片服务器，并且将返回的图片地址存到数据库。图片删除只删除数据库中的数据，不删除服务器的图片文件。页面通过图片服务器的url进行图片展示。


FastDFS安装见博客地址：[FastDFS图片服务器安装步骤及遇到的问题博客目录](https://blog.csdn.net/qq_40389276/category_9515622.html)


![图片管理](https://images.gitee.com/uploads/images/2021/0130/210207_d7c1ae04_5573516.png "图片.png")



- 上传图片到服务器
>将图片上传到图片服务器FastDFS，并将图片访问地址保存到数据库。

代码见：[上传图片代码](src\main\java\com\sfs\filesystem\controller\FileSystemController.java) 

- 图片信息管理
> 包含查询图片信息、保存图片信息、删除图片信息。

代码见：[图片信息管理代码](src\main\java\com\sfs\managecourse\controller\CourseController.java) 


##### 课程章节管理


##### 课程发布
























#### 媒资管理
> 媒资管理模块是教学管理中心的一个子模块，主要包含上传文件和我的媒资两个模块。我的媒资模块包括文件查询和文件删除等操作，教学管理员登陆系统后可以进行上传文件和管理媒资文件等操作。


**模块地址**

[sfs-service-manage-media](sfs-service-manage-media)

[sfs-service-manage-media-processor](sfs-service-manage-media-processor)

##### 上传文件
---
![上传文件](https://images.gitee.com/uploads/images/2021/0130/202257_02b8ae44_5573516.png "上传文件.png")

1. 文件断点续传
>上传文件模块实现断点续传将视频文件保存到指定路径，由于上传的文件多是视频文件，文件大小较大，所以采用了断点续传的上传方案来保证系统的高可用。在上传文件开始时将文件分割为多个块文件，分别上传块文件，到所有块文件上传结束合并这些块文件生成和原始一致的文件。如果中途文件上传失败，下次上传时可从上次失败的块文件开始上传，大大的节省系统资源。

代码见：[断点续传代码Controller类](src\main\java\com\sfs\managemedia\controller\MediaUploadController.java)

开发文档见：[视频上传第一步-断点续传开发文档](https://gitee.com/zztiyjw/sfsProject/wikis/pages?sort_id=3482288&doc_id=1219120)


2. 文件处理
>由于页面流媒体播放协议选用了HLS协议，所以还需要将上传后的视频文件进行处理，将视频文件转换成m3u8格式的视频文件，实现视频的边下载边播放。

代码见：[媒资处理mq消费者类](src\main\java\com\sfs\mediaprocess\mq\MediaProcessTask.java)

开发文档见：[视频上传第二步-文件处理开发文档](https://gitee.com/zztiyjw/sfsProject/wikis/pages?sort_id=3482289&doc_id=1219120)


##### 我的媒资
---
> 我的媒资可以对上传后的媒资信息进行管理，可查询媒资文件，删除媒资文件。删除媒资文件只删除数据库里的媒资数据，不删除本地媒资文件。


![我的媒资](https://images.gitee.com/uploads/images/2021/0130/202311_218af0fa_5573516.png "我的媒资.png")


代码见：[我的媒资Controller类](src\main\java\com\sfs\managemedia\controller\MediaFileController.java)



### 系统管理中心
>系统管理员是系统管理中心重要的参与者，主要是对系统的用户信息、管理员信息和前台页面信息进行管理，系统管理员还负责对数据库数据以及前台相关的数据信息进行管理。

>系统管理中心有内容管理和系统管理两个模块。内容管理系统主要是对前台页面内容进行管理，通过该模块可以实现实时修改前台页面显示的内容。系统管理模块包含用户管理、角色管理等模块，主要是对整个系统的权限相关的信息进行管理。


#### CMS内容管理模块

#### 系统管理模块








