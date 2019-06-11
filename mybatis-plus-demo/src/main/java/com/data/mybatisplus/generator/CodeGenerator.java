package com.data.mybatisplus.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

//演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

 /**
  * <p>
  * 读取控制台内容
  * </p>
  */
 public static String scanner(String tip) {
     Scanner scanner = new Scanner(System.in);
     StringBuilder help = new StringBuilder();
     help.append("请输入" + tip + "：");
     System.out.println(help.toString());
     if (scanner.hasNext()) {
         String ipt = scanner.next();
         if (StringUtils.isNotEmpty(ipt)) {
             return ipt;
         }
     }
     throw new MybatisPlusException("请输入正确的" + tip + "！");
 }

 public static void main(String[] args) {
     // 代码生成器
     AutoGenerator mpg = new AutoGenerator();

     // 全局配置
     GlobalConfig gc = new GlobalConfig();
     String projectPath = System.getProperty("user.dir");
     gc.setOutputDir(projectPath + "/mybatis-plus-demo/src/main/java");
     gc.setAuthor("ln");
     gc.setOpen(true);
     gc.setServiceName("%sService");//service 命名方式
     gc.setServiceImplName("%sServiceImpl");//service impl 命名方式
     // 自定义文件命名，注意 %s 会自动填充表实体属性！
     gc.setMapperName("%sMapper");
     gc.setXmlName("%sMapper");
     gc.setBaseResultMap(true);
     mpg.setGlobalConfig(gc);

     // 数据源配置
     DataSourceConfig dsc = new DataSourceConfig();
     //dsc.setUrl("jdbc:mysql://cdb-ixddeoay.bj.tencentcdb.com:10085/nc?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&autoReconnect=true&failOverReadOnly=false");
     dsc.setUrl("jdbc:mysql://52baojie.cn:3307/clean?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&autoReconnect=true&failOverReadOnly=false");
     // dsc.setSchemaName("public");
     dsc.setDriverName("com.mysql.jdbc.Driver");
     dsc.setUsername("aliluning");
     dsc.setPassword("root");
     mpg.setDataSource(dsc);

     // 包配置
     PackageConfig pc = new PackageConfig();
     //pc.setModuleName(scanner("模块名"));
     pc.setParent("com.data.mybatisplus");
     pc.setEntity("entity.pojo");
     pc.setService("service");
     pc.setServiceImpl("service.impl");
     pc.setController("controller");//设置控制器包名
     pc.setMapper("dao");
     pc.setXml("dao");
     mpg.setPackageInfo(pc);

     /*// 自定义配置
     InjectionConfig cfg = new InjectionConfig() {
         @Override
         public void initMap() {
             // to do nothing
         }
     };

     // 如果模板引擎是 freemarker
     String templatePath = "/templates/mapper.xml.ftl";
     // 如果模板引擎是 velocity
     // String templatePath = "/templates/mapper.xml.vm";

     // 自定义输出配置
     List<FileOutConfig> focList = new ArrayList<>();
     // 自定义配置会被优先输出
     focList.add(new FileOutConfig() {
         @Override
         public String outputFile(TableInfo tableInfo) {
             // 自定义输出文件名
             return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                     + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
         }
     });

     cfg.setFileOutConfigList(focList);
     mpg.setCfg(cfg);*/

     /*// 配置模板
     TemplateConfig templateConfig = new TemplateConfig();

     // 配置自定义输出模板
     // templateConfig.setEntity();
     // templateConfig.setService();
     // templateConfig.setController();
     
     templateConfig.setXml(null);
     mpg.setTemplate(templateConfig);*/

     // 策略配置
     StrategyConfig strategy = new StrategyConfig();
     strategy.setNaming(NamingStrategy.underline_to_camel);
     strategy.setColumnNaming(NamingStrategy.underline_to_camel);
     //strategy.setSuperEntityClass("com.wgw.smnc.entity");
     strategy.setEntityLombokModel(true);
     strategy.setRestControllerStyle(true);
	//strategy.setSuperControllerClass("com.wgw.smnc.controller");
     strategy.setInclude(scanner("请输入表名").split(","));
     //strategy.setSuperEntityColumns("id");
     strategy.setControllerMappingHyphenStyle(true);
     //strategy.setTablePrefix("nc_");
     strategy.entityTableFieldAnnotationEnable(true);
     mpg.setStrategy(strategy);
     //mpg.setTemplateEngine(new FreemarkerTemplateEngine());
     mpg.execute();
 }

}