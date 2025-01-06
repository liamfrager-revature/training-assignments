public class MessageQueue {

    public static void main(String[] args) {
        Broker b = new Broker();

        Producer p1 = new Producer(b);
        Producer p2 = new Producer(b);

        Consumer c1 = new Consumer();
        Consumer c2 = new Consumer();

        // Test functionality
        p2.produceMessage();

        b.subscribe(c1);
        p1.produceMessage();
        p2.produceMessage();

        b.subscribe(c2);
        p2.produceMessage();
        p1.produceMessage();

        b.unsubscribe(c1);
        p1.produceMessage();
        p2.produceMessage();

        b.unsubscribe(c2);
        p1.produceMessage();
    }
}
