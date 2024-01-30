# Java agent 探针部署

> 说明：
> 1. 本说明和文件只适用本次环境。
> 2. agent文件需要在各个docker swarm 节点解压并初始化，且需要保证所有节点路径一致。
> 3. docker 配置修改只针对需要被监控的 java服务。
> 4. 以下说明均以路径 `/data/apm/nfs/skywalking/skywalking-agent` 为例，可根据实际情况修改。

## Step1: 准备文件
将 `apm-skywalking-java-agent-9.1.0.tgz` 文件上传到目录，这里以`/data/apm/nfs/skywalking`目录为例。

并执行 `tar xzvf apm-skywalking-java-agent-9.1.0.tgz`解压压缩包

```shell
[root@apm-master skywalking]# pwd
/data/apm/nfs/skywalking
[root@apm-master skywalking]# ls -al
总用量 35096
drwxr-xr-x  3 root root       73 1月  24 18:16 .
drwxr-xr-x 21 root root     4096 1月  17 14:06 ..
-rw-r--r--  1 root root 35932982 1月  24 18:16 apm-skywalking-java-agent-9.1.0.tgz
drwxr-xr-x 10 root root      221 1月   8 15:27 skywalking-agent
```
**至此文件的准备工作已完成。需要注意的点就是，以上操作需要在所有的docker swarm节点执行，且所有节点解压路径保持一致**

**记住上述解压文件路径，这里以`/data/apm/nfs/skywalking/skywalking-agent/`为例。**

## Step2: docker参数配置修改

修改需要监控的Java程序对应的docker yml 文件部分

docker yml 参数只需要修改三个地方：
1. 路径映射,需要将第一步中的路径映射到对应的服务中 `"/data/apm/nfs/skywalking/skywalking-agent:/data/apm/nfs/skywalking/skywalking-agent"`，注意文件权限修改，确保docker 映射后有权限操作该文件夹。
2. 修改`JAVA_OPTS`环境变量，添加 -javaagent:/data/apm/nfs/skywalking/skywalking-agent/skywalking-agent.jar， 
**需要注意的是如果原先`JAVA_OPTS`已经包含`-javaagent:/..../skywalking-agent.jar`的参数，需要移除确保只有一个这样的配置，否则项目可能会出错**。
3. 修改添加环境变量`SW_AGENT_NAME=szmc-um`，其中的值为服务名称 如：`"szmc-um"`。

以下为示例配置
```yaml
 szmc-bcs:
    #...
    #...
    environment:
      ...
      # 如果原先已经包含 -javaagent:/..../skywalking-agent.jar 的参数，需要先移除
      # 添加 -javaagent:/data/apm/nfs/skywalking/skywalking-agent/skywalking-agent.jar
      - JAVA_OPTS=-Xms256M -Xmx256M -Xmn256M -XX:MetaspaceSize=256M -javaagent:/data/apm/nfs/skywalking/skywalking-agent/skywalking-agent.jar
      # 添加环境变量SW_AGENT_NAME，其值为docker服务名称。我观察地铁环境以前有这个配置，确保存在和配置正确即可
      - SW_AGENT_NAME=szmc-bcs
      ...
    volumes:
      ...
      # 路径映射 和第一步中的路径保持一致，注意文件权限修改，确保docker 映射后有权限操作该文件夹
      - /data/apm/nfs/skywalking/skywalking-agent:/data/apm/nfs/skywalking/skywalking-agent/
      ...
```
至此docker相关配置已完成，重启相关服务即可。

## 其他说明
以上说明均以路径 `/data/apm/nfs/skywalking/skywalking-agent` 为例，可根据实际情况修改，但需要注意docker配置中JAVA_OPTS参数和文件映射的对应修改。
