public class Message {

    private static int idIndex = 0;

    public Message(int producerId) {
        idIndex += 1;
        this.id = idIndex;
        this.producerId = producerId;
    }

    public int id;
    public int producerId;
}
