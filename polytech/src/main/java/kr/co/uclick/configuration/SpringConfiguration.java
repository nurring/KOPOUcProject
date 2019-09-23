package kr.co.uclick.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ignite.cache.hibernate.HibernateRegionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration //의존 관계 설정
@ImportResource(locations = "classpath:applicationContext-ignite.xml")
@ComponentScan({ "kr.co.uclick.service" })
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableSpringConfigured
@EnableJpaRepositories(basePackages = "kr.co.uclick.repository")
public class SpringConfiguration {

	@Bean //@Bean어노테이션은 새로운 빈 객체를 제공할 때 사용되며, @Bean이 적용된 메서드의 이름을 빈의 식별값으로 사용
	@Primary //pk
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.56.102:3306/kopo05");		
		dataSource.setUsername("root");
		dataSource.setPassword("dc190105");
		return dataSource;
	}

	@Bean
	@DependsOn("igniteSystem") //빈이 등록되는 순서 : igniteSystem 다음! ~ Spring Boot에서만.. Spring에서는 알아서 한다 ~ https://jeong-pro.tistory.com/167
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		//LocalContainerEntityManagerFactoryBean: SessionFactory에 대한 Factory Bean
		//SessionFactory를 생성하는 객체를 등록시킴. 이는 Spring에서 사용할 DataSource와 Entity가 위치한 Package들에 대한 검색을 모두 포함
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource()); // @Configuration으로 묶여 의존관계가 설정되어 있기 때문에 이렇게 가져다 쓸 수 있다(bean이 두개가 생기는 것이 아님)
		em.setPackagesToScan("kr.co.uclick.entity");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); //Hibernate vendor과 JPA간의 Adapter를 설정
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		//Hibernate용 TransactionManager임. @Transactional annotation을 사용할 수 있게 됩
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	public Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
		properties.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString());
		properties.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());
		properties.setProperty(AvailableSettings.DIALECT, MySQL5Dialect.class.getName());

		properties.setProperty(AvailableSettings.STATEMENT_BATCH_SIZE, "1000");

		properties.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, Boolean.TRUE.toString());
		properties.setProperty(AvailableSettings.USE_QUERY_CACHE, Boolean.TRUE.toString());
		properties.setProperty(AvailableSettings.GENERATE_STATISTICS, Boolean.TRUE.toString());
		properties.setProperty(AvailableSettings.CACHE_REGION_FACTORY, HibernateRegionFactory.class.getName());

		properties.setProperty("org.apache.ignite.hibernate.ignite_instance_name", "cafe-grid");
		properties.setProperty("org.apache.ignite.hibernate.default_access_type", "NONSTRICT_READ_WRITE");

		properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY,
				CustomPhysicalNamingStrategyStandardImpl.class.getName());
		return properties;
	}

}
