package cn.bestsort.authority.config;

import cn.bestsort.authority.filter.TokenFilter;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger文档
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		ParameterBuilder builder = new ParameterBuilder();
		builder.parameterType("header").name(TokenFilter.TOKEN_KEY)
				.description("header参数")
				.required(false)
				.modelRef(new ModelRef("string"));
				// 在swagger里显示header

		return new Docket(DocumentationType.SWAGGER_2).groupName("swagger接口文档")
				.apiInfo(new ApiInfoBuilder().title("swagger接口文档")
						.contact(new Contact("LIC", "", "1824461232@qq.com")).version("1.0").build())
				.globalOperationParameters(Lists.newArrayList(builder.build()))
				.select().paths(PathSelectors.any()).build();
	}
}
