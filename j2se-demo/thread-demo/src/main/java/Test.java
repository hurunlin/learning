public class Test {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private boolean a = true;

    public Test() {
    }

    public Test setId(int id) {
        this.id = id;
        return this;
    }

    public Test setName(String name) {
        this.name = name;
        return this;
    }

    public Test setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Test setAddress(String address) {
        this.address = address;
        return this;
    }

    public Test printId() {
        System.out.println(this.id);
        return this;
    }

    public Test printName() {
        System.out.println(this.name);
        return this;
    }

    public Test printPhoneNumber() {
        System.out.println(this.phoneNumber);
        return this;
    }

    public Test printAddress() {
        System.out.println(this.address);
        return this;
    }

    public void setA(boolean a) {
        System.out.println(this.a = false);
    }

    public Test getA() {
        System.out.println(this.a);
        return this;
    }
}

class A {
    public static void main(String[] args) {
        Test test = new Test();
        test.setId(3).setName("John").setPhoneNumber("1111111").setAddress("US").setA(false);
        test.printId()
                .printName()
                .printPhoneNumber()
                .printAddress()
                .getA();
    }
}

