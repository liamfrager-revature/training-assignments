import java.util.ArrayList;
import java.util.List;

public class Broker {

    private List<Consumer> subscribers = new ArrayList<Consumer>();
    
    public void subscribe(Consumer subscriber) {
        subscribers.add(subscriber);
        System.out.println("Consumer #" + subscriber.id + " subscribed to the broker.");
    }

    public void unsubscribe(Consumer subscriber) {
        subscribers.remove(subscriber);
        System.out.println("Consumer #" + subscriber.id + " unsubscribed from the broker.");
    }

    public void processMessage(Message message) {
        if (subscribers.size() > 0) {
            for (Consumer subscriber : subscribers) {
                subscriber.consume(message);
            }
        } else {
            System.err.println("There are no consumers subscribed to receive messages.");
        }
    }

}
