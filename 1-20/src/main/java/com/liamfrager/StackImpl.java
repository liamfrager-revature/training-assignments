

public class StackImpl<T> {

    private T val;
    private StackImpl<T> next;
    private int size = 0;

    public StackImpl() {}

    public void push(T newVal) {
        if (newVal != null) {
            StackImpl<T> newNode = new StackImpl<T>();
            newNode.val = this.val;
            newNode.next = this.next;
            this.next = newNode;
            this.val = newVal;
            this.size++;
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
        if (popVal != null) this.size--;
        return popVal;
    }

    public T remove(int index) {
        if (index >= this.size || index < 0) {
            return null;
        }
        if (index == 0) {
            return this.pop();
        } else {
            StackImpl<T> nodeAtIndex = this.next;
            int i = 1;
            while (i < index) {
                nodeAtIndex = nodeAtIndex.next;
                i++;
            }
            this.size--;
            return nodeAtIndex.pop();
        }
    }

    public int size() {
        return this.size;
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