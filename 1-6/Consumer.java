public class Consumer {
    
    private static int idIndex = 0;

    public Consumer() {
        idIndex += 1;
        this.id = idIndex;
    }

    public int id;

    public void consume(Message message) {
        System.out.println("Consumer #" + this.id + " recieved message #" + message.id + " from producer #" + message.producerId + "."); 
    }
    
}
