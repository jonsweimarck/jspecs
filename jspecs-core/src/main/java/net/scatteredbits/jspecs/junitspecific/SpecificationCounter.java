package net.scatteredbits.jspecs.junitspecific;


import java.util.concurrent.atomic.AtomicInteger;

public enum SpecificationCounter {
    INSTANCE;

    private AtomicInteger counter = new AtomicInteger(0);

    public void inc(){
        counter.incrementAndGet();
    }

    public void dec(){
        if(counter.decrementAndGet() == 0){
            log();
        }
    }

    private void log(){
        System.out.println("READY");
    }

}
