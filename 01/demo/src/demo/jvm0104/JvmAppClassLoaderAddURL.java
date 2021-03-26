package demo.jvm0104;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * <p>
 * 自定义类加载器
 * </p>
 *
 * @SINCE 2021/3/26 14:44
 * @AUTHOR CodeByck-write
 */
public class JvmAppClassLoaderAddURL {
    public static void main(String[] args) {
        String appPath = "file:/I:/极客大学/Java进阶训练营/第一周 JVM 进阶/demo/src/demo/jvm0104";
        URLClassLoader urlClassLoader = (URLClassLoader) JvmAppClassLoaderAddURL.class.getClassLoader();
        try {
            Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURL.setAccessible(true);
            URL url = new URL(appPath);
            addURL.invoke(urlClassLoader, url);
            Class.forName("demo.jvm0104.Hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
