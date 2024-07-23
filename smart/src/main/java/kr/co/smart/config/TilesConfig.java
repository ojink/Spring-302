package kr.co.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration //설정클래스로 등록
public class TilesConfig {

	@Bean
	TilesViewResolver tilesViewResolver() {
		TilesViewResolver view = new TilesViewResolver();
		view.setViewClass( TilesView.class );
		view.setOrder(0);
		return view;
	}
	
	@Bean
	TilesConfigurer tilesConfigurer() {
		TilesConfigurer config = new TilesConfigurer();
		config.setDefinitions( "/WEB-INF/views/tiles/config.xml" );
		return config;
	}
}
