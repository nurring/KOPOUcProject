package kr.ac.kopo.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration //config 클래스라고 명시
@EnableWebMvc //Spring Web Mvc 설정 클래스라고 명시하기. 자동으로 WebMvcConfigurationSupport 클래스가 Bean에 등록된다.
@ComponentScan("kr.ac.kopo.controller")
public class SpringWebConfiguration implements WebMvcConfigurer {
//@Component와 stereo(@Service, @Repository, @Controller)어노테이션이 부여된 class를 자동으로 scan하여 bean으로 등록
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);//확장자로 contentType을 구분할 것인지 여부. 우선 순위 가장 높다
		configurer.favorParameter(true); //특정 파라미터로 contentType을 구분할 것인지 여부
		configurer.parameterName("mediaType");
		configurer.ignoreAcceptHeader(true); //XML,Json등 호출하는 URL이 Return값에 따라 변하게 할 것인지에 대한 설정.
											 //(true 설정시 return받는 데이터타입에 따라 return 된다. Json->Json) 
		configurer.useJaf(false);
		configurer.mediaType("xml", MediaType.APPLICATION_XML); //매핑할 contentType을 작성
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Override
	//Interceptor는 Dispatcher Servlet의 뒤의 Handler 영역에서 요청 정보를 처리
	public void addInterceptors(InterceptorRegistry registry) { //등록할 인터셉터를 설정
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		//@RequestMapping에 등록되지 않은 요청 또는 JSP에 대한 요청을 처리하는 DefaultServletHandler 설정(이곳으로 요청전달됨)
		//A common use case for this is when the DispatcherServlet is mapped to "/" thus overriding the Servlet container's default handling of static resources.
		configurer.enable();
	}

	@Bean
	public LocaleResolver LocaleResolver() { //locale : 국가/지역 별 환경설정
		return new CookieLocaleResolver(); //쿠키를 이용해서 Locale 정보를 저장
	}

	@Bean
	public MessageSource messageSource() {
		//국제화(i18n)을 제공하는 인터페이스. 메세지 설정 파일을 모아놓고 각 국가마다 로컬라이징하여 쉽게 각 지역에 맞춘 메세지를 제공 
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		// 메시지를 읽어오는 MessageSource 구현체
		resourceBundleMessageSource.setBasename("validate-message.properties");
		return resourceBundleMessageSource;
	}

	@Bean
	public InternalResourceViewResolver InternalResourceViewResolver() {
		//WEB-INF 하위의 jsp파일에 직접(URL입력 등) 접근하지 못하게 함(Controller를 통해서만 가능)
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}
}
