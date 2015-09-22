package net.scatteredbits.jspecs.builders;

/**
 * Created by jons on 01/11/15.
 */
public abstract class  Method <T, U> {


    public void execute(T t, U u){
        // do nothing default
    }

    public void execute(T t){
        // do nothing default
    }
}
