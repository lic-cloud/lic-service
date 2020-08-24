package cn.bestsort.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 16:33
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LicException extends RuntimeException {

    /**
     * Error errorData.
     */
    private String msg;

    private int code;

}
