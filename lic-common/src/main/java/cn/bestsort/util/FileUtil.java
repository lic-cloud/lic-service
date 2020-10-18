package cn.bestsort.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import org.apache.commons.lang.StringUtils;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-15 09:26
 */
public class FileUtil {

    /**
     * 写入文件
     * @param target       目标路径
     * @param src          文件源
     * @throws IOException IO Exception
     */
    public static void write(File target, InputStream src) throws IOException {
        OutputStream os = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            os.write(buf,0,len);
        }
        os.flush();
        os.close();
    }

    /**
     * 分块写入文件
     * @param target        目标路径
     * @param targetSize    文件大小
     * @param src           文件源
     * @param srcSize       源大小（可能被切分）
     * @param chunks        块总数
     * @param chunk         块索引
     * @throws IOException  IO Exception
     */
    public static void writeWithBlock(String target, Long targetSize,
                                      InputStream src, Long srcSize,
                                      Integer chunks, Integer chunk) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(target, "rw");
        randomAccessFile.setLength(targetSize);
        if (chunk == chunks - 1) {
            randomAccessFile.seek(targetSize - srcSize);
        } else {
            randomAccessFile.seek(chunk * srcSize);
        }
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            randomAccessFile.write(buf,0,len);
        }
        randomAccessFile.close();
    }


    public static String unionPath(String...args) {
        StringBuilder builder = new StringBuilder();
        // Windows 系统不需要以文件分割符开头，但是Linux需要
        boolean needAppendSep = !System.getProperty("os.name").startsWith("Windows");
        for (String arg : args) {
            if (!arg.startsWith(File.separator)) {
                if (needAppendSep) {
                    builder.append(File .separator);
                }
                needAppendSep = true;
            }
            builder.append(StringUtils.stripEnd(arg, File.separator));
        }
        return builder.toString();
    }
}
