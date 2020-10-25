# ID生成器

### 简介

    ider-spring-boot-starter，是基于关系型数据库mysql/h2提供一个带有步长的累加的Id生成器（Long/Stirng）。 
    本版本0.0.1.GA是第一个稳定版本，可以用于系统的Id生成。

### 配置及调用

    1. 在properties文件中配置datasource(DuridDataSource)
    
    2. 在properties文件中配置流水号list：
    
        step-id-factory.input : 为固定配置的key
        t1：为某类ID的标记，比如：某个表名
        10000000000：累加起始值
        4：程序一次获取的步长
        测试类型t1：描述
    
        例如：
            step-id-factory.input.t1=t1,10000000000,4,测试类型t1
            step-id-factory.input.t2=t2,10000000000,8,测试类型t2
            
    3. 程序中调用：注入StepIdFactory;
    
        @Autowird
        private StepIdFactory stepIdFactory;
        调用:
        String id = stepIdFactory.getFactory("t1").generateString();
        
    4. 参考test工程:
        
        
### 默认依赖
        
    1. lombok (idea需要配置插件)
    2. DuridDataSource (目前不支持替换)
    3. mysql/h2 (默认已经有的数据库驱动，可自行替换其他的)
    
# 版本升级

    第一阶段：
        实现集群Id生成器；
    第二阶段：
        实现集群的无限扩容；
        
# 联系

    王先生
    wangliyun0987@outlook.com
    13371943540