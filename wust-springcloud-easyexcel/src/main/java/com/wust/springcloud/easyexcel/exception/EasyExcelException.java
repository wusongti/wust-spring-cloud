package com.wust.springcloud.easyexcel.exception;

/**
 * 异常处理
 */
public class EasyExcelException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

    public EasyExcelException() {
        super();
    }

    public EasyExcelException(String message) {
        super(message);
    }

    public EasyExcelException(String code, String message) {
        super(message);
        this.code = code;
    }

    public EasyExcelException(Throwable cause) {
        super(cause);
    }

    public EasyExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyExcelException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
