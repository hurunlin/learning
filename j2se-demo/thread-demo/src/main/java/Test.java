public class Test {
    @Override
    public String toString() {
        return "test toString";
    }
}
class B extends Test {
    String a = "11111111";
    String b;
    public B(String b) {
        this.b = b;
    }
    @Override
    public String toString() {
        return "b toString " + b;
    }
}
class C extends Test {
}

class A {
    public static void main(String[] args) {
        B b = new B("22222222");
        System.out.println(b);
        System.out.println(b.a);
        C c = new C();
        System.out.println(c);
    }
}

