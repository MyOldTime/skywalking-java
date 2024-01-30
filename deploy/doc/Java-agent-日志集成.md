# Java-Agent日志采集配置

## logback
1. 项目中添加依赖
    ```xml
     <dependency>
        <groupId>org.apache.skywalking</groupId>
        <artifactId>apm-toolkit-logback-1.x</artifactId>
        <version>9.1.0</version>
    </dependency>
    ```
2. 在`logback`配置文件`logback.xml`中添加 `GRPCLogClientAppender`
    ```xml
    <appender name="apm-log" class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.log.GRPCLogClientAppender">
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.mdc.TraceIdMDCPatternLogbackLayout">
                <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{tid}] [%thread] %-5level %logger{36} -%msg%n</Pattern>
            </layout>
        </encoder>
    </appender>
    ```
3. 将定义的`appender`关联到输出的节点上，如：
    ```xml
    <root level="info">
        <appender-ref ref="apm-log"  />
        <appender-ref ref="STDOUT"/>
    </root>
    ```
4. 完整的`logback.xml`示例如下：
    ```xml
   <?xml version="1.0" encoding="UTF-8"?>
    <configuration scan="true" scanPeriod=" 5 seconds">
        <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
                <layout class="ch.qos.logback.classic.PatternLayout">
                    <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} -%msg%n</Pattern>
                </layout>
            </encoder>
        </appender>
    
        <!-- 关注点1：添加 GRPCLogClientAppender-->
        <appender name="apm-log" class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.log.GRPCLogClientAppender">
            <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
                <layout class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.mdc.TraceIdMDCPatternLogbackLayout">
                    <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{tid}] [%thread] %-5level %logger{36} -%msg%n</Pattern>
                </layout>
            </encoder>
        </appender>
    
        <root level="info">
            <appender-ref ref="STDOUT"/>
             <!-- 关注点2：添加 appender-ref 日志输出-->
            <appender-ref ref="apm-log"  />
        </root>
    </configuration>
   ```
## log4j2
1. 项目中添加依赖
    ```xml
     <dependency>
         <groupId>org.apache.skywalking</groupId>
         <artifactId>apm-toolkit-log4j-2.x</artifactId>
         <version>9.1.0</version>
     </dependency>
    ```
2. 在`log4j2`配置文件`log4j2.xml`中添加 `GRPCLogClientAppender`
    ```xml
    <GRPCLogClientAppender name="apm-log">
        <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
    </GRPCLogClientAppender>
    ```
3. 将定义的`appender`关联到输出的节点上，如：
    ```xml
    <loggers>
        <root level="INFO">
            <appender-ref ref="Console"/>
            <appender-ref ref="apm-log"/>
        </root>
    </loggers>
    ```
4. 完整的`log4j2.xml`示例如下：
    ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <configuration status="WARN" monitorInterval="30">
   
       <!--定义变量-->
       <properties>
           <property name="LOGS_BASE">logback/surfilter-apm-server</property>
       </properties>
   
       <appenders>
           <console name="Console" target="SYSTEM_OUT">
               <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%level] [%thread] [%logger:%line] %msg%n"/>
           </console>
   
           <!-- 关注点1：添加 GRPCLogClientAppender-->
           <GRPCLogClientAppender name="apm-log">
               <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
           </GRPCLogClientAppender>
   
       </appenders>
   
       <loggers>
           <root level="INFO">
               <appender-ref ref="Console"/>
               <!-- 关注点2：添加 appender-ref 日志输出-->
               <appender-ref ref="apm-log"/>
           </root>
       </loggers>
   
   </configuration>
   ```
## log4j
1. 项目中添加依赖
   ```xml
   <dependency>
      <groupId>org.apache.skywalking</groupId>
      <artifactId>apm-toolkit-log4j-1.x</artifactId>
      <version>9.1.0</version>
   </dependency>
   ```
2. 在`log4j`配置文件`log4j.properties`中添加 `GRPCLogClientAppender`
   ```properties
   log4j.appender.ApmAppender=org.apache.skywalking.apm.toolkit.log.log4j.v1.x.log.GRPCLogClientAppender
   log4j.appender.ApmAppender.layout=org.apache.log4j.PatternLayout
   log4j.appender.ApmAppender.layout.ConversionPattern=[%t] %-5p %c %x - %m%n
   ```
3. 将定义的`appender`关联到输出的节点上，如：
    ```properties
     log4j.rootLogger=info, stdout, ApmAppender
    ```
4. 完整的`log4j.properties`示例如下：
    ```properties
   #关注点2：添加 日志输出 appender 到 rootLogger
   log4j.rootLogger=info, stdout, ApmAppender
   
   log4j.appender.stdout=org.apache.log4j.ConsoleAppender
   log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
   log4j.appender.stdout.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss}] %5p [%t] (%F:%L) - %m%n
   
   # 关注点1：添加 GRPCLogClientAppender
   log4j.appender.ApmAppender=org.apache.skywalking.apm.toolkit.log.log4j.v1.x.log.GRPCLogClientAppender
   log4j.appender.ApmAppender.layout=org.apache.log4j.PatternLayout
   log4j.appender.ApmAppender.layout.ConversionPattern=[%d{yyyy-MM-dd HH:mm:ss}] %5p [%t] (%F:%L) - %m%n
   ```
## java.util.logger
1. 将我们提供的`apm-toolkit-log-jul-java8-9.1.0.jar` 放入项目`pom`文件同级文件夹`lib`下
2. 项目中添加依赖
   ```xml
    <dependency>
        <groupId>org.apache.skywalking</groupId>
        <artifactId>apm-toolkit-log-jul-java8</artifactId>
        <version>9.1.0</version>
        <scope>system</scope>
        <systemPath>${pom.basedir}/lib/apm-toolkit-log-jul-java8-9.1.0.jar</systemPath>
    </dependency>
   ```
3. 在`java.util.logger`自定义配置文件`logging.properties`中添加 `GRPCLogClientHandler`
    ```properties
    org.apache.skywalking.apm.toolkit.log.jul.java8.log.GRPCLogClientHandler.level=INFO
    org.apache.skywalking.apm.toolkit.log.jul.java8.log.GRPCLogClientHandler.formatter=org.apache.skywalking.apm.toolkit.log.jul.java8.JulFormatter
4. 将定义的`handler`添加到`handlers`中，如：
    ```properties
   handlers=java.util.logging.ConsoleHandler,org.apache.skywalking.apm.toolkit.log.jul.java8.log.GRPCLogClientHandler
   ```
5. 完整的`logging.properties`示例如下：
    ```properties
   .level=INFO
    #关注点2：添加 GRPCLogClientHandler 到 handlers
    handlers=java.util.logging.ConsoleHandler,org.apache.skywalking.apm.toolkit.log.jul.java8.log.GRPCLogClientHandler
    
    java.util.logging.ConsoleHandler.level=INFO
    java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter
    
    # 关注点1：添加 GRPCLogClientHandler
    org.apache.skywalking.apm.toolkit.log.jul.java8.log.GRPCLogClientHandler.level=INFO
    org.apache.skywalking.apm.toolkit.log.jul.java8.log.GRPCLogClientHandler.formatter=org.apache.skywalking.apm.toolkit.log.jul.java8.JulFormatter
   ```