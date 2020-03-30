//package com.github.xia.security.admin.generator;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @explain：generator代码生成器
// * @author: XIA
// * @date: 2020-02-03
// * @since: JDK 1.8
// * @version: 1.0
// */
//public class CodeGenerator {
//    public static void main(String[] args) {
//        GlobalConfig config = new GlobalConfig();
//        config.setSwagger2(true);
//        String dbUrl = "jdbc:mysql://localhost:3306/micro_stock?useSSL=false&useUnicode=true&characterEncoding=utf-8";
//        DataSourceConfig dataSourceConfig = new DataSourceConfig();
//        dataSourceConfig.setDbType(DbType.MYSQL)
//                .setUrl(dbUrl)
//                .setUsername("micro_user")
//                .setPassword("micro_user")
//                .setDriverName("com.mysql.cj.jdbc.Driver");
//        StrategyConfig strategyConfig = new StrategyConfig();
//        //排除生成的表
//        //strategyConfig.setExclude(new String[]{""});
//        //需要生成的表
//        strategyConfig.setInclude(new String[]{"base_resource_authority"});
//        strategyConfig.setCapitalMode(true)
//                //这里结合了Lombok，所以设置为true，如果没有集成Lombok，可以设置为false
//                .setEntityLombokModel(true)
//                .setNaming(NamingStrategy.underline_to_camel);
//        //这里因为我是多模块项目，所以需要加上子模块的名称，以便直接生成到该目录下，如果是单模块项目，可以将后面的去掉
//        String projectPath = System.getProperty("user.dir") +"/ace-admin";;
//        System.out.println("项目路径=============="+projectPath);
//        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//            }
//        };
//        // 如果模板引擎是 freemarker
//        //String templatePath = "/templates/mapper.xml.ftl";
//        // 如果模板引擎是 velocity
//         String templatePath = "/templates/mapper.xml.vm";
//
//        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名
//                return projectPath + "/src/main/resources/mapper/"
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//
//        cfg.setFileOutConfigList(focList);
//
//        //设置作者，输出路径，是否重写等属性
//        config.setActiveRecord(false)
//                .setEnableCache(false)
//                .setAuthor("xia")
//                .setOutputDir(projectPath + "/src/main/java")
//                .setFileOverride(true)
//                .setServiceName("%sService");
//        new AutoGenerator()
//                .setGlobalConfig(config)
//                .setDataSource(dataSourceConfig)
//                .setStrategy(strategyConfig)
//                .setTemplateEngine(new VelocityTemplateEngine())
//                .setCfg(cfg)
//                //这里进行包名的设置
//                .setPackageInfo(
//                        new PackageConfig()
//                                .setParent("com.github.xia.security.admin")
//                                .setController("controller")
//                                .setEntity("entity")
//                                .setMapper("mapper")
//                                .setServiceImpl("service.impl")
//                                .setService("service")
//                ).execute();
//    }
//}
