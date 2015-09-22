package net.scatteredbits.jspecs.builders;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TwoArgMethodTest {

    private String s;
    private Integer i;

    @Test
    public void executeShouldCallFunction(){
        new TwoArgMethod<>(this::setStringAndInteger).execute("Banana", 42);
        assertThat(s, is("Banana"));
        assertThat(i, is(42));
    }

    public void setStringAndInteger(String s, Integer i){
        this.s = s;
        this.i = i;
    }
}