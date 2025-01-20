public class App {
    public static void main(String[] args) {
        StackImpl<Integer> stack = new StackImpl<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.print();
        stack.remove(1);
        stack.print();
    }
}
