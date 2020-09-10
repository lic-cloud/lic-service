package cn.bestsort;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 08:29
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "lic")
public class StaterProperties {
    private Boolean generatorFakeData = false;
}
