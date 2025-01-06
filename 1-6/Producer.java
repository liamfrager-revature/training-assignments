public class Producer {

    private static int idIndex = 0;

    public Producer(Broker broker) {
        idIndex += 1;
        this.id = idIndex;
        this.broker = broker;
    }

    private int id;
    private Broker broker;

    public void produceMessage() {
        Message message = new Message(this.id);
        System.out.println("Producer #" + this.id + " sent message #" + message.id + ".");
        this.broker.processMessage(message);
    }
}
