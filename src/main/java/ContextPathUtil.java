public class ContextPathUtil {
    public static int getIdFromPathInfo(String pathInfo) {
        if (pathInfo == null || pathInfo.equals("/")) {
            return -1;
        }

        try {
            return Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID format in pathInfo.", e);
        }
    }
}
