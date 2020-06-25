### 1. 使用方式
## 1.1 配置及调用
    1. 在properties文件中配置datasource.
    2. 在properties文件中配置流水号list：step-id-factory.input.
    3. 程序中调用：注入StepIdFactory，
        @Autowird
        private StepIdFactory stepIdFactory;
        调用
        String id = stepIdFactory.getFactory("t1").generateString();
     4. 参考test工程:
        
        
## 1.2 默认依赖        
    1. lombok (idea需要配置插件)
    2. DuridDataSource (目前不支持替换)
    3. h2/mysql (默认已经有的数据库驱动，可自行替换其他的)
    
### 2. 简介
    Idfactory，基于关系型数据库提供一个带有步长的累加的Id生产器（Stirng）。 
    本版本0.0.1.GA是第一个稳定版本，可以用于系统的Id生成。
    
### 3. 版本升级
    第一阶段：
        实现集群Id生成器；
    第二阶段：
        实现集群的无限扩容；
        
### 4. 联系
    354057600@qq.com
    王利云