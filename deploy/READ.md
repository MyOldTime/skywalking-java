> 1. 本说明为 java-agent 的部署文件准备说明
> 2. 请按照 [Java-agent-探针文件准备.md](Java-agent-探针文件准备.md) 的步骤操作，制作最终探针部署包
> 3. 下面为本目录的文件结构


```
+-- deploy
    +-- config
          agent.config.default
          agent.config.default
          change_config.sh
    +-- doc
          Java-agent-探针部署说明.md
          Java-agent-日志集成.md
    +-- lib (若为空，请按[Java-agent-探针文件准备.md]第一步操作)
         apm-toolkit-log-jul-java8-activation-9.1.0.jar
         apm-toolkit-log-jul-java8-9.1.0.jar
    Java-agent-探针文件准备.md
    README.md
```