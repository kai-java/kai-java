package demo.jvm0104;

import sun.misc.Launcher;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * <p>
 * 显示当前 ClassLoader 加载了哪些 Jar
 * </p>
 *
 * @SINCE 2021/3/26 14:18
 * @AUTHOR CodeByck-write
 */
public class JvmClassLoaderPrintPath {
    public static void main(String[] args) {
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL url : urLs) {
            System.out.println(" ==> " + url.toExternalForm());
        }

        // 扩展类加载器
        printClassLoader("扩展类加载器", JvmClassLoaderPrintPath.class.getClassLoader());
        // 应用类加载器
        printClassLoader("应用类加载器", JvmClassLoaderPrintPath.class.getClassLoader());
    }

    public static void printClassLoader(String name, ClassLoader CL) {
        if (CL != null) {
            System.out.println(name + " classLoader ==> " + CL.toString());
            printURLForClassLoader(CL);
        } else {
            System.out.println(name + " classLoader ==> " + null);
        }
    }

    public static void printURLForClassLoader(ClassLoader CL) {
        Object ucp = insightField(CL, "ucp");
        Object path = insightField(ucp, "path");
        ArrayList ps = (ArrayList) path;
        for (Object p : ps) {
            System.out.println(" ==> " + p.toString());
        }

    }

    public static Object insightField(Object obj, String fname) {
        try {
            Field f = null;
            if (obj instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fname);
            } else {
                f = obj.getClass().getDeclaredField(fname);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
