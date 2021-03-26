package demo.jvm0104;

/**
 * <p>
 * 字节码
 * </p>
 *
 * @SINCE 2021/3/24 17:00
 * @AUTHOR CHENKAIFANG
 */
public class HelloByteCode {
    public static void main(String[] args) {
        HelloByteCode helloByteCode = new HelloByteCode();
        byte[] hellow_worlds = new String("Hellow world").getBytes();
        System.out.println(hellow_worlds);
    }
}
