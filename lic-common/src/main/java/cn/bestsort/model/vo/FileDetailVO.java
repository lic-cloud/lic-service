package cn.bestsort.model.vo;

import java.util.List;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.entity.FileShare;
import lombok.Data;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-11-22 20:29
 */
@Data
public class FileDetailVO {
    FileMapping mapping;
    List<FileShare> shares;
}
