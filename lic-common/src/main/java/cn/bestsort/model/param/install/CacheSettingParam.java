package cn.bestsort.model.param.install;

import lombok.Data;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 10:26
 */

@Data
public class CacheSettingParam {
    String  host          = "127.0.0.1";
    Integer port          = 6379;
    String  password      = null;
    Integer maxTotal      = Runtime.getRuntime().availableProcessors() * 2;
    Integer maxIdle       = Runtime.getRuntime().availableProcessors() * 2;
    Integer minIdle       = 4;
    Integer maxWaitMillis = 1000;
}
