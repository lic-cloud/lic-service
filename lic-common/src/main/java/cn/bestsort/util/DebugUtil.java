package cn.bestsort.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import cn.bestsort.model.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.ResourceUtils;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 08:13
 */

@Slf4j
public class DebugUtil {
    public static final String ALL_CLASS_NAME = "class_pkg";
    public static final String ALL_DATA       = "all-data";
    public static final String DATA           = "data";
    public static final String DATA_FILE      = "classpath:DEBUG_DATA";


    public static String loadDebugData() throws IOException {
        File file        = ResourceUtils.getFile(DATA_FILE);
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader reader      = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        // 构建 InputStreamReader 对象，编码与写入相同
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append((char) reader.read());
            // 转成 char 加到 StringBuffer 对象中
        }
        return sb.toString();
    }

    /**
     * 部分实体字段落库时需要进行字段转换, 在这里进行
     */
    public static void filedAudit(List<Object> target) {
        if (CollectionUtils.isEmpty(target)) {
            return;
        }
        if (target.get(0) instanceof User) {
            target.forEach(o -> {
                User user = (User) o;
                user.setUserPassword(BCrypt.hashpw(user.getUserPassword(), BCrypt.gensalt()));
            });
        }
    }
}
