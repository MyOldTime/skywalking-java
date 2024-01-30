## 1. 编译本项目
查看本文件所在文件夹 `lib` 中是否存在 ：
1. `apm-toolkit-log-jul-java8-activation-9.1.0.jar`
2. `apm-toolkit-log-jul-java8-9.1.0.jar`

如果不存在需要执行插件编译，以下是编译的注意事项，如果有则跳过编译这个步骤

重点关注一下项目的编译，项目中有 `READ.md` 文件
1. apm-application-toolkit/apm-toolkit-log-jul-java8
2. apm-sniffer/apm-toolkit-activation/apm-toolkit-log-jul-java8-activation

编译完后将文件放入本文件所在文件夹 `lib` 中

## 2. 从官网下载 `apache-skywalking-java-agent-9.1.0.tgz`

下载地址为：[apache-skywalking-java-agent-9.1.0.tgz](https://dlcdn.apache.org/skywalking/java-agent/9.1.0/apache-skywalking-java-agent-9.1.0.tgz "apache-skywalking-java-agent-9.1.0.tgz")，
或者通过 [skywalking官网](https://skywalking.apache.org/downloads) 找到 9.1.0 版本 java-agent下载

## 3. 准备文件和插件

将下载的文件 `apache-skywalking-java-agent-9.1.0.tgz` 上传到任意的 `Linux` 服务器，并执行 `tar xzvf apache-skywalking-java-agent-9.1.0.tgz`解压压缩包
```shell
[root@apm-master skywalking]# ls -al
总用量 35096
drwxr-xr-x  3 root root       73 1月  24 18:16 .
drwxr-xr-x 21 root root     4096 1月  17 14:06 ..
-rw-r--r--  1 root root 35932982 1月  24 18:16 apache-skywalking-java-agent-9.1.0.tgz
drwxr-xr-x 10 root root      221 1月   8 15:27 skywalking-agent
```

1. 执行 `cp skywalking-agent/optional-reporter-plugins/kafka-reporter-plugin-9.1.0.jar skywalking-agent/plugins/` 安装安选插件  
2. 将本目录的下 `change_config.sh`  复制到 `skywalking-agent/` 下  
3. 将本目录的下 `config/agent.config.default` `config/agent.config.default` 复制到 `skywalking-agent/config` 中
4. 将本目录的下 `lib/apm-toolkit-log-jul-java8-activation-9.1.0.jar` 复制到 `skywalking-agent/activations` 中


以上步骤完成后，`skywalking-agent` 目录结构如下

```shell
[root@localhost skywalking-agent]# tree
.
├── activations
    ...
│   ├── apm-toolkit-kafka-activation-9.1.0.jar
    ...
    ...
├── change_config.sh
├── config
│   ├── agent.config.default
│   └── agent.config.template
    ...
    ...
├── plugins
    ...
│   ├── kafka-reporter-plugin-9.1.0.jar
    ...
└── skywalking-agent.jar

```

## 4. 修改配置文件

进入目录`cd skywalking-agent`并执行执行初始化配置

`sh skywalking-agent/change_config.sh sz_metro 10.12.119.152:11800 10.12.119.152:19092,10.12.119.152:19093,10.12.119.152:19094`

上述脚本有3个参数：
1. 应用名称： 暂时地铁项目可以固定为 `sz_metro`
2. 服务地址：待apm系统部署完毕后，oap的地址，例 `10.12.119.152:11800`
3. kafka地址：待apm系统部署完毕后，kafka地址，例 `10.12.119.152:19092,10.12.119.152:19093,10.12.119.152:19094`

## 5. 打包并交付文件说明

执行 `tar czvf apm-skywalking-java-agent-9.1.0.tgz skywalking-agent` 得到 `apm-skywalking-java-agent-9.1.0.tgz` 。

最后交付给客户部署的说明文件有：
1. 打包后的文件 apm-skywalking-java-agent-9.1.0.tgz
2. doc下面  Java-agent-探针部署说明.md


下面两个看后续的规划，是否对采集日志这块进行改造，如果改造成通过java-agent采集，这下面两个文件说明也需要交付他们，他们需集按说明成改造。（tips: 后续需要 看采集日志类别，来修改说明文档）

3. Java-agent-日志集成.md
4. lib下面 apm-toolkit-log-jul-java8-9.1.0.jar

## 6. 其他

后续有机会可以将这个文件描述的脚本化处理。