package seg.playground.pms_back.common.config;

import jakarta.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import seg.playground.pms_back.common.config.support.RoutingDataSource;

@Configuration
@MapperScan(
        basePackages = {
                "seg.playground.pms_back.*.mapper",
                "seg.playground.pms_back.*.*.mapper"
        },
        sqlSessionTemplateRef = "sqlSessionTemplate")
@EnableJpaRepositories(
        basePackages = {
                "seg.playground.pms_back.*.repository",
                "seg.playground.pms_back.*.*.repository"
        }
)
@RequiredArgsConstructor
public class DataConfig {

    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean("writeDataSource")
    @ConfigurationProperties("spring.datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("readDataSource")
    @ConfigurationProperties("spring.datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("routingDataSource")
    public DataSource routingDataSource(@Qualifier("writeDataSource") DataSource writeDataSource, @Qualifier("readDataSource") DataSource readDataSource) throws Exception {
        return new RoutingDataSource(writeDataSource, Collections.singletonList(readDataSource));
    }

    @Primary
    @Bean("dataSource")
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Primary
    @Bean("entityManagerFactory")
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan(
                "seg.playground.pms_back.*.domain",
                "seg.playground.pms_back.*.*.domain"
        );
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);
        localContainerEntityManagerFactoryBean.afterPropertiesSet();

        return localContainerEntityManagerFactoryBean.getObject();
    }

    @Primary
    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

    @Primary
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("seg.playground.pms_back.*.domain;seg.playground.pms_back.*.*.domain");
        Objects.requireNonNull(sqlSessionFactoryBean.getObject()).getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}