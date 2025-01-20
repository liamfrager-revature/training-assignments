import org.junit.jupiter.api.*;

import StackImplementation.StackImpl;

import static org.junit.jupiter.api.Assertions.*;


public class StackImplTest
 {

   StackImpl<Integer> stack;

    @BeforeEach
    public void beforeEach() {
        this.stack = new StackImpl<Integer>();
    }
    
    @Test
    public void pushTest() {
        this.stack.push(1);
        assertEquals(1, stack.peek());
    }

    @Test
    public void peekTest() {
        assertNull(this.stack.peek());
        this.stack.push(1);
        this.stack.push(2);
        assertEquals(2, stack.peek());
        this.stack.pop();
        assertEquals(1, stack.peek());
    }

    @Test
    public void popTest() {
        this.stack.push(1);
        this.stack.push(2);
        this.stack.push(3);
        assertEquals(3, this.stack.pop());
        assertEquals(2, this.stack.pop());
        assertEquals(1, this.stack.pop());
        assertNull(this.stack.pop());
    }

}
