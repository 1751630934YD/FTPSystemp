//package com.itheima.ftpfile.Mybatis;
//
//import com.itheima.ftpfile.mapper.usermapper;
//import org.apache.commons.dbcp2.BasicDataSource;
//
//import org.apache.ibatis.datasource.pooled.PooledDataSource;
//
//import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.apache.ibatis.transaction.TransactionFactory;
//import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Configuration
//public class SqlSessionconfig {
//
//    private static Environment env;
//
//    @Autowired
//    public void setEnv(Environment env) {
//        this.env = env;
//    }
//    public static SqlSessionFactory  sqlSession(){
//
//
////        DataSource dataSource = new PooledDataSource(getBlogDataSource());
////        TransactionFactory transactionFactory = new JdbcTransactionFactory();
////        org.apache.ibatis.mapping.Environment environment =new org.apache.ibatis.mapping.Environment.Builder("development").transactionFactory(transactionFactory).dataSource(dataSource).build();
////        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
////        configuration.addMappers("com.itheima.ftpfile.mapper");
////
////        String resource = "mapper/mybatisconfig.xml";
////        try {
////            InputStream inputStream = Resources.getResourceAsStream(resource);
////            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
////            return  sqlSessionFactory;
////        } catch (
////                IOException e) {
////            throw new RuntimeException(e);
////        }
//
//    }
//    public static UnpooledDataSource getBlogDataSource() {
//
//        UnpooledDataSource dataSource = new UnpooledDataSource();
//        dataSource.setDriver(env.getProperty("spring.datasource.driver-class-name"));
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));
//        return dataSource;
//    }
//}
