package cn.bestsort.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-12-12 17:20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileUploadVO {
    Integer status;

    Integer fileIndex;

    public static ResponseEntity<FileUploadVO> ok(Integer fileIndex) {
        return ResponseEntity.ok(new FileUploadVO(FileUploadVO.NEED_UPLOAD, fileIndex));
    }
    public static ResponseEntity<FileUploadVO> exist() {
        return ResponseEntity.ok(new FileUploadVO(FileUploadVO.EXIST, null));
    }

    public static ResponseEntity<FileUploadVO> overLimit() {
        return ResponseEntity.ok(new FileUploadVO(FileUploadVO.OVER_LIMIT, null));
    }

    private final static Integer NEED_UPLOAD = 1001;
    /**
     * 文件已存在
     */
    private final static Integer EXIST = 1002;
    /**
     * 超出容量限制
     */
    private final static Integer OVER_LIMIT = 1003;

}
