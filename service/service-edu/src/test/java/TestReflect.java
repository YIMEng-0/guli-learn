/**
 * @author Doraemon
 * @date 2022/5/15 11:08 上午
 * @version 1.0
 */


public class TestReflect {
    public static void main(String[] args) {
        TestReflect testReflect = new TestReflect();
        Class te = testReflect.getClass();

        Class te1 = TestReflect.class;


        System.out.println("类加载器" + te.getClassLoader());


        System.out.println("---");
        System.out.println(te);
    }
}
