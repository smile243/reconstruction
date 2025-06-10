import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Property;
import lombok.extern.slf4j.Slf4j;

/**
 * mybatis-plus 自动生成类
 */
@Slf4j
public class GeneratorMybatisPlus {

    //需要配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&autoReconnect=true";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_TABLES = "bd_material";
    private static final String PACKAGE_NAME = "org.yjl";
    private static final String AUTHOR = "yjl";
    private static final Boolean ENABLE_SWAGGER = true;
    public static void main(String[] args) {

        // 1.数据源配置
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig
                .Builder(
                DB_URL,
                DB_USERNAME,
                DB_PASSWORD
        );

        // 2.全局配置
        GlobalConfig.Builder globalConfigBuilder = new GlobalConfig.Builder();
        // 代码生成目录
        String projectPath = System.getProperty("user.dir");
        globalConfigBuilder.outputDir(projectPath + "/yjl-springboot-demo/src/main/java");
        // 作者
        globalConfigBuilder.author(AUTHOR);
        globalConfigBuilder.disableOpenDir();
        // 结束时是否打开文件夹
        // 日期类型处理
        globalConfigBuilder.dateType(DateType.ONLY_DATE);
        // 实体属性Swagger2注解
        if (ENABLE_SWAGGER) {
            globalConfigBuilder.enableSwagger();
        }
        // 3.包配置
        PackageConfig.Builder packageConfigBuilder = new PackageConfig.Builder();
        packageConfigBuilder.parent(PACKAGE_NAME);
        packageConfigBuilder.mapper("mapper");
        packageConfigBuilder.entity("domain.entity");
        packageConfigBuilder.service("service");
        packageConfigBuilder.serviceImpl("service.impl");
        // 4.策略配置
        StrategyConfig.Builder strategyConfigBuilder = new StrategyConfig.Builder();
        // 设置需要映射的表名  用逗号分割
        strategyConfigBuilder.addInclude(DB_TABLES.split(","));
        // 下划线转驼峰
        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.entityBuilder().columnNaming(NamingStrategy.underline_to_camel);
        // entity的Lombok
        strategyConfigBuilder.entityBuilder().enableLombok();
        strategyConfigBuilder.entityBuilder().enableTableFieldAnnotation();
        // 自动填充
        // 创建时间
        IFill gmtCreate = new Property("gmt_create", FieldFill.INSERT);
        // 更新时间
        IFill gmtModified = new Property("gmt_modified", FieldFill.INSERT_UPDATE);
        strategyConfigBuilder.entityBuilder().addTableFills(gmtCreate, gmtModified);
        // 乐观锁
        strategyConfigBuilder.entityBuilder().versionColumnName("version");
        strategyConfigBuilder.entityBuilder().versionPropertyName("version");
        strategyConfigBuilder.entityBuilder().formatFileName("%sPo");
        strategyConfigBuilder.entityBuilder().idType(IdType.ASSIGN_ID);
        strategyConfigBuilder.serviceBuilder().formatServiceFileName("%sService");
        strategyConfigBuilder.serviceBuilder().formatServiceImplFileName("%sServiceImpl");
        strategyConfigBuilder.mapperBuilder().formatMapperFileName("%sMapper");
        strategyConfigBuilder.mapperBuilder().formatMapperFileName("%sMapper");

        // 创建代码生成器对象，加载配置   对应1.2.3.4步
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfigBuilder.build());
        autoGenerator.global(globalConfigBuilder.build());
        autoGenerator.packageInfo(packageConfigBuilder.build());
        autoGenerator.strategy(strategyConfigBuilder.build());
        TemplateConfig.Builder builder = new TemplateConfig.Builder();
        builder.controller("");
        autoGenerator.template(builder.build());
        // 执行
        autoGenerator.execute();

    }
}