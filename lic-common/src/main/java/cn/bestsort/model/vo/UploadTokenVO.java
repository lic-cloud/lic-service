package cn.bestsort.model.vo;

import java.util.Map;

import lombok.Data;

/**
 * 文件上传流程如下:
 *    web     --> service: 请求上传
 *    service --> web    : 返回 token 和 host，以及一些拓展配置
 *    web     --> host   : 根据拓展配置，带token上传文件到对应host
 * @author bestsort
 * @version 1.0
 * @date 2020-09-15 09:03
 */

@Data
public class UploadTokenVO {
    String              host;
    String              token;
    Map<String, String> extConfig;
}
