package com.example;

public class SyncThreadCounter {

        class SynchronizedCounter {
            private int count = 0;

            public synchronized void increment() {
                count++;
            }

            public int getCount() {
                return count;
            }
        }

        class UnsynchronizedCounter {
            private int count = 0;

            public void increment() {
                count++;
            }

            public int getCount() {
                return count;
            }
        }

        public void run() {
            System.out.println("// COUNTING TO 1000, TWICE //");

            SynchronizedCounter sCounter = new SynchronizedCounter();
            UnsynchronizedCounter uCounter = new UnsynchronizedCounter();

            Runnable r1 = () -> {
                for (int i = 0; i < 1000; i++) {
                    sCounter.increment();
                    uCounter.increment();
                }
            };

            Runnable r2 = () -> {
                for (int i = 0; i < 1000; i++) {
                    sCounter.increment();
                    uCounter.increment();
                }
            };

            Thread t1 = new Thread(r1);
            Thread t2 = new Thread(r2);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

            System.out.println("FINISHED SYNCHRONIZED COUNTING: " + sCounter.getCount());
            System.out.println("FINISHED UNSYNCHRONIZED COUNTING: " + uCounter.getCount());
            System.out.println();
        }
    }