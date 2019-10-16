package kr.ac.kopo.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
//	web.xml 없이도 서블릿 컨텍스트 초기화 작업이 가능해짐
//	web.xml: 서블릿 등록/매핑, 리스너 등록, 필터 등록 같은 작업
//	ServletContainerInitializer : 프레임워크 레벨에서 직접 초기화할 수 있게 도와주는 API
//	이 인터페이스를 구현한 클래스를 만들어두면 웹 어플리케이션이 시작할 때 자동으로 onStartup() 메서드가 실행됨
	
	@Override
	public void onStartup(ServletContext servletCxt) {

		// 루트 웹 애플리케이션 컨텍스트 등록(root-context.xml)
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfiguration.class);
//		실제 설정 정보는 springConfiguration.java에 있다
		
		// 리스너 등록
//		WebApplicationInitializer의 onStartup()은 서블릿 컨텍스트 초기화 시점에 실행된다고 했으니 리스너를 등록하지 않아도 된다고 생각할 수 있으나,
//		초기화 시점만 잡을 수 있지 종료 시점을 잡을수 없으므로 위와 같이 리스너는 등록해줘야 한다.
//		종료 시점을 잡아주지 않으면 애플리케이션이 종료되어도 리소스가 반환되지 못하므로 메모리 누수가 일어날 수 있다.
		servletCxt.addListener(new ContextLoaderListener(rootContext));
	
		// 서블릿 웹 애플리케이션 컨텍스트 등록(servlet-context.xml)
		//서블릿 웹 애플리케이션 컨텍스트는 서블릿 안에서 초기화되고 서블릿이 종료될 떄 같이 종료된다.
		//이때 사용되는 서블릿이 DispatcherServlet
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SpringWebConfiguration.class);

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = servletCxt.addServlet("politech",
				new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		//필터나 리스너들도 이런식으로 모두 등록할 수 있음
		//인코딩
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		servletCxt.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(null, false,
				"/*");
	}

}
