import java.lang.reflect.InvocationTargetException;

public class DemoApplication {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Animal a = new Animal();
        Animal b = new Animal();

        a.barkMessage = "aaaa";
        b.barkMessage = "bbbb";

        Animal.class.getMethod("bark").invoke(a);
        Animal.class.getMethod("bark").invoke(b);
    }


}
class Animal {

    public String barkMessage;

    public void bark() {
        System.out.println(barkMessage);
    }
}