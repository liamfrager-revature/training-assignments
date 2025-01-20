import org.junit.jupiter.api.*;

import StackImplementation.StackImpl;

import static org.junit.jupiter.api.Assertions.*;


public class StackImplTest
 {

   StackImpl<Integer> stack;

    @BeforeEach
    public void beforeEach() {
        this.stack = new StackImpl<Integer>();
        this.stack.push(1);
        this.stack.push(2);
        this.stack.push(3);
    }
    
    @Test
    public void pushTest() {
        this.stack.push(1);
        assertEquals(1, stack.peek());
    }

    @Test
    public void peekTest() {
        assertNull(this.stack.peek());
        assertEquals(3, stack.peek());
        this.stack.pop();
        assertEquals(2, stack.peek());
    }

    @Test
    public void popTest() {
        assertEquals(3, this.stack.pop());
        assertEquals(2, this.stack.pop());
        assertEquals(1, this.stack.pop());
        assertNull(this.stack.pop());
    }

    @Test
    public void sizeTest() {
        assertEquals(3, this.stack.size());
    }

}
