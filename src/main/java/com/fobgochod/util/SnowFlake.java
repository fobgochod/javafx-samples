package com.fobgochod.util;

public class SnowFlake {
    private static final long START_STAMP = 1544965696000L;
    private static final long SEQUENCE_BIT = 6L;
    private static final long MACHINE_BIT = 3L;
    private static final long DATA_CENTER_BIT = 3L;
    private static final long MAX_DATA_CENTER_NUM = 7L;
    private static final long MAX_MACHINE_NUM = 7L;
    private static final long MAX_SEQUENCE = 63L;
    private static final long MACHINE_LEFT = 6L;
    private static final long DATA_CENTER_LEFT = 9L;
    private static final long TIMESTAMP_LEFT = 12L;
    private static SnowFlake ourInstance;
    private long dataCenterId = 1L;
    private long machineId = 1L;
    private long sequence = 0L;
    private long lastStamp = -1L;

    private SnowFlake() {
    }

    public static SnowFlake getInstance() {
        if (ourInstance == null) {
            Class var0 = SnowFlake.class;
            synchronized (SnowFlake.class) {
                if (ourInstance == null) {
                    ourInstance = new SnowFlake();
                }
            }
        }

        return ourInstance;
    }

    public void init(long dataCenterId, long machineId) {
        if (dataCenterId <= 7L && dataCenterId >= 0L) {
            if (machineId <= 7L && machineId >= 0L) {
                this.dataCenterId = dataCenterId;
                this.machineId = machineId;
            } else {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }
        } else {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
    }

    public synchronized long newId() {
        long currStamp = this.getNewStamp();
        if (currStamp < this.lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        } else {
            if (currStamp == this.lastStamp) {
                this.sequence = this.sequence + 1L & 63L;
                if (this.sequence == 0L) {
                    currStamp = this.getNextMill();
                }
            } else {
                this.sequence = 0L;
            }

            this.lastStamp = currStamp;
            return currStamp - 1544965696000L << 12 | this.dataCenterId << 9 | this.machineId << 6 | this.sequence;
        }
    }

    private long getNextMill() {
        long mill;
        for (mill = this.getNewStamp(); mill <= this.lastStamp; mill = this.getNewStamp()) {
        }

        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }
}
