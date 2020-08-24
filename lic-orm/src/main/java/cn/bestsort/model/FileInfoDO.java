package cn.bestsort.model;

import java.sql.Timestamp;

import cn.bestsort.model.enums.FileNamespace;
import lombok.Data;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 11:04
 */

@Data
public class FileInfoDO {
    String path;
    String owner;
    String md5;
    Boolean dir;
    FileNamespace namespace;
    Timestamp uploadTime;
    Timestamp lastModified;
}
