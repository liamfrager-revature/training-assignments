package com.example;

import org.junit.jupiter.api.*;

public class SyncThreadCounterTest {

    private SyncThreadCounter syncThreadCounter = new SyncThreadCounter();
    
    @Test
    public void runTest() {
        Assertions.assertEquals(0, syncThreadCounter.sCounter.getCount());
        Assertions.assertEquals(0, syncThreadCounter.uCounter.getCount());
        syncThreadCounter.run();
        Assertions.assertEquals(2000, syncThreadCounter.sCounter.getCount());
        Assertions.assertNotNull(syncThreadCounter.uCounter.getCount());
    }
}
