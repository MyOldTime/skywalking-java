> 官方` skywalking-java-agent`对于采集日志只适配了log4j、log4j2、logback, 该项目是为了适配对 `java.util.logger` (目前该项目只针对jdk1.8)的支持。
>
> 编译该项目时，需要使用jdk8编译，编译级别选择8，不能只用高级别jdk更改编译级别进行编译

该项目编译后会生成 `apm-toolkit-log-jul-java8-activation-9.1.0.jar`  
需要将这个编译的文件放入到 agent 的 activations 目录下

```
+-- agent
    +-- activations
        ...
         apm-toolkit-log-jul-java8-activation-9.1.0.jar
         ...
    +-- config
         agent.config  
    +-- plugins
         .....
    +-- optional-plugins
         .....
    +-- bootstrap-plugins
         .....
    +-- logs
    skywalking-agent.jar
```
