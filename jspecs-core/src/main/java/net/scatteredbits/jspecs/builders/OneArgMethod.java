package net.scatteredbits.jspecs.builders;

import java.util.function.Consumer;


public class OneArgMethod<T> extends Method<T, Object> {

    private Consumer<T> oneArgFunc;

    public OneArgMethod(Consumer<T> oneArgFunc) {
        this.oneArgFunc = oneArgFunc;
    }


    @Override
    public void execute(T t) {
        oneArgFunc.accept(t);
    }

}
