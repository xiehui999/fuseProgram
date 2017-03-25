package comm.bridge;

public class Main {
    public static void main(String[] args) {
        RandomCountDisplay d = new RandomCountDisplay(new StringDisplayImpl("Hello, China."));
        d.randomDisplay(10);

        Display d1 = new Display(new StringDisplayImpl("Hello, China."));
        Display d2 = new CountDisplay(new StringDisplayImpl("Hello, World."));
        CountDisplay d3 = new CountDisplay(new StringDisplayImpl("Hello, Universe."));
        d1.display();
        d2.display();
        d3.display();
        d3.multiDisplay(5);
    }
}
