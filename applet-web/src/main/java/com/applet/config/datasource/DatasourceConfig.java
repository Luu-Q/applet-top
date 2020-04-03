package com.applet.config.datasource;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableTransactionManagement
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DatasourceConfig extends HikariConfig {

    @Primary
    @Bean(name = "teacherDSConfig")
    @ConfigurationProperties(prefix = "spring.datasource.teacher")
    public HikariConfig teacherDataSourceConfig() {
        return new HikariConfig();
    }

    @Bean(name = "teacherDatasource", destroyMethod = "close")
    @Primary
    public DataSource teacherDatasource(@Qualifier("teacherDSConfig") HikariConfig dsConfig) {
        final HikariDataSource ds = new HikariDataSource(dsConfig);
        return ds;
    }

    @Bean(name = "courseDSConfig")
    @ConfigurationProperties(prefix = "spring.datasource.course")
    public HikariConfig courseDataSourceConfig() {
        return new HikariConfig();
    }

    @Bean(name = "courseDatasource", destroyMethod = "close")
    public DataSource courseDatasource(@Qualifier("courseDSConfig") HikariConfig dsConfig) {
        final HikariDataSource ds = new HikariDataSource(dsConfig);
        return ds;
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(@Qualifier("teacherDatasource") DataSource teacherDatasource,
                                        @Qualifier("courseDatasource") DataSource courseDatasource) {
        Map<Object, Object> targetDataSource = new HashMap<>();
//        targetDataSource.put(DataSourceType.DataBaseType.teacher, teacherDatasource);
//        targetDataSource.put(DataSourceType.DataBaseType.course, courseDatasource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(teacherDatasource);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory dataSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource,
                                                   org.apache.ibatis.session.Configuration configuration)
            throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setPlugins(new Interceptor[]{
                paginationInterceptor()});
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis-plus.configuration")
    public org.apache.ibatis.session.Configuration configuration() {
        return new org.apache.ibatis.session.Configuration();
    }

}
