package net.scatteredbits.jspecs.builders;

import java.util.function.BiConsumer;

public class TwoArgMethod<T, U> extends Method<T, U> {

    private BiConsumer<T, U> twoArgFunc;

    public TwoArgMethod(BiConsumer<T, U> twoArgFunc) {
        this.twoArgFunc = twoArgFunc;
    }

    @Override
    public void execute(T t, U u) {
        twoArgFunc.accept(t, u);
    }
}
