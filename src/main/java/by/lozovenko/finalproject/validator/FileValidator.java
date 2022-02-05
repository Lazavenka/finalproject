package by.lozovenko.finalproject.validator;

public class FileValidator {
    private static final String CONTENT_BMP = "image/bmp";
    private static final String CONTENT_PNG = "image/png";
    private static final String CONTENT_JPEG = "image/jpeg";
    private static final String EXTENSION_JPEG = ".jpeg";
    private static final String EXTENSION_JPG = ".jpg";
    private static final String EXTENSION_PNG = ".bmp";
    private static final String EXTENSION_BMP = ".png";
    private static final int MAX_SIZE = 1024 * 1024;

    private FileValidator() {
    }

    public static boolean checkSize(long size) {
        return size > 0 && size <= MAX_SIZE;
    }

    public static boolean checkExtension(String extension) {
        return extension != null && (extension.equals(EXTENSION_JPG) || extension.equals(EXTENSION_BMP) || extension.equals(EXTENSION_PNG) || extension.equals(EXTENSION_JPEG));
    }

    public static boolean checkContentType(String contentType) {
        return contentType != null && (contentType.equals(CONTENT_BMP) || contentType.equals(CONTENT_PNG) || contentType.equals(CONTENT_JPEG));
    }
}
