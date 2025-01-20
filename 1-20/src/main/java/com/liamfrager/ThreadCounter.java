class Main {

    static class CounterThread implements Runnable {
        String name;

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(name + " => " + i);
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        CounterThread(String name) {
            this.name = name;
        }
    }
    public static void main(String[] args) {

        Thread t1 = new Thread(new CounterThread("T1"));
        Thread t2 = new Thread(new CounterThread("T2"));

        t1.start();
        t2.start();

        System.out.println("Thread execution begun...");

        try {
            t1.join();
            System.out.println("Thread 1 execution completed!");
            t2.join();
            System.out.println("Thread 2 execution completed!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Thread execution completed!");
    }
}