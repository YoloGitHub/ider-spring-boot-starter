package com.tolo.idFactory.configuration;

import com.tolo.idFactory.impl.step.StepIdFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootConfiguration
@EnableConfigurationProperties
public class IderConfig {

//    @Autowired
//    public ConfigurableApplicationContext applicationContext; //不用这种注入方式，又可能是null，spring加载顺序所致，使用构造方法获取

    //在springboot中，因为引入的是Druid-starter，这个是自动装配，不用配置这个，配了会报错，如果要配，而可以使用下面的方式

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dateSource() {
//
//        return new DruidDataSource();
//    }

    //这个高版本的springboot 2.3.0.RELEASE + Druid 1.1.22 +mybatis-plus3.3.2,自动装配不行了，必须要用下面这样
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid")
//    public DruidDataSource dataSource() {
//        return DruidDataSourceBuilder.create().build();
//    }

//    // 第二种方式
//    @Bean
//    public DataSource dateSource(ApplicationContext applicationContext) {
//
//        return DruidDataSourceBuilder.create().build(applicationContext.getEnvironment(), "spring.datasource.");
//    }

    @Bean
    @ConfigurationProperties(prefix = "step-id-factory")
    public StepIdFactory stepIdFactory(ApplicationContext applicationContext){

        StepIdFactory stepIdFactory = new StepIdFactory();
        stepIdFactory.setDataSource(applicationContext.getBean(DataSource.class));

        return stepIdFactory;
    }

}
