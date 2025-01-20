import StackImplementation.StackImpl;

public class App {
    public static void main(String[] args) {
        StackImpl<Integer> stack = new StackImpl<Integer>();
        stack.push(1);
        stack.push(null);
        stack.push(null);
        stack.push(null);
        stack.print();
        stack.pop();
        stack.print();
        stack.pop();
        stack.print();
        stack.push(5);
        stack.print();
        stack.pop();
        stack.print();
        stack.pop();
        stack.print();
    }
}
