package StackImplementation;

public class StackImpl<T> {

    private T val;
    private StackImpl<T> next;

    public StackImpl() {}

    public void push(T newVal) {
        if (newVal != null) {
            StackImpl<T> newNode = new StackImpl<T>();
            newNode.val = this.val;
            newNode.next = this.next;
            this.next = newNode;
            this.val = newVal;
        }
    }

    public T peek() {
        return this.val;
    }

    public T pop() {
        T popVal = this.val;
        if (this.next == null) {
            this.val = null;
            this.next = null;
        } else {
            this.val = next.val;
            this.next = this.next.next;
        }
        return popVal;
    }

    public void print() {
        if (this.val == null) {
            System.out.println("[]");
        } else {
            System.out.println("[" + this.getValue() + "]");
        }
    }

    private String getValue() {
        if (this.next.val == null) {
            return this.val.toString();
        } else {
            return this.val + ", " + this.next.getValue();
        }
    }
}