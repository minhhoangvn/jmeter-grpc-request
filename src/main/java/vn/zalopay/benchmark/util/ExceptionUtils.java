package vn.zalopay.benchmark.util;

import com.alibaba.fastjson.JSONObject;

/**
 * <b>Exception print utility class</b>
 *
 * <p>reference:
 * https://github.com/yl-yue/yue-library/blob/j11.2.6.0/yue-library-base/src/main/java/ai/yue/library/base/util/ExceptionUtils.java
 *
 * @author ylyue
 * @since 2018-9-9
 */
public class ExceptionUtils {

    @SuppressWarnings("unchecked")
    private static synchronized <T> T getPrintException(
            Throwable e, Integer line, ExceptionConvertEnum exceptionConvertEnum) {
        T msg = null;
        if (exceptionConvertEnum == ExceptionConvertEnum.JSONObject) {
            msg = (T) new JSONObject(true);
            if (e == null) {
                ((JSONObject) msg).put("0", "The stack trace is null");
                return msg;
            } else {
                ((JSONObject) msg).put("0", e.toString());
            }
        } else if (exceptionConvertEnum == ExceptionConvertEnum.StringBuffer) {
            msg = (T) new StringBuffer();
            if (e == null) {
                ((StringBuffer) msg).append("The stack trace is null");
                return msg;
            } else {
                ((StringBuffer) msg).append(e + "\n");
            }
        }

        StackTraceElement[] stackTraceElementArray = e.getStackTrace();
        int maxLine = stackTraceElementArray.length;
        if (line == null) {
            line = maxLine;
        } else {
            line = line > maxLine ? maxLine : line;
        }

        for (int i = 0; i < line; i++) {
            StackTraceElement stackTraceElement = stackTraceElementArray[i];
            String fileName = stackTraceElement.getFileName();
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            int lineNumber = stackTraceElement.getLineNumber();

            if (exceptionConvertEnum == ExceptionConvertEnum.JSONObject) {
                ((JSONObject) msg)
                        .put(
                                i + 1 + "",
                                "　　at "
                                        + className
                                        + "."
                                        + methodName
                                        + "("
                                        + fileName
                                        + ":"
                                        + lineNumber
                                        + ")");
            }

            if (exceptionConvertEnum == ExceptionConvertEnum.StringBuffer) {
                ((StringBuffer) msg)
                        .append(
                                "\tat "
                                        + className
                                        + "."
                                        + methodName
                                        + "("
                                        + fileName
                                        + ":"
                                        + lineNumber
                                        + ")\n");
            }
        }

        return msg;
    }

    /**
     * Get print abnormal content
     *
     * @param e exception
     * @param line Print the number of rows
     * @return exception data
     */
    public static synchronized String getPrintExceptionToStr(Throwable e, Integer line) {
        StringBuffer printException = getPrintException(e, line, ExceptionConvertEnum.StringBuffer);
        return printException.toString();
    }

    private enum ExceptionConvertEnum {
        JSONObject,
        StringBuffer;
    }
}
