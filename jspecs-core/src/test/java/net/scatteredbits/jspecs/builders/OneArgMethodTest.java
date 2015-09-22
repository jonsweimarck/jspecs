package net.scatteredbits.jspecs.builders;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OneArgMethodTest {

    private String s;

    @Test
    public void executeShouldCallFunction(){
        new OneArgMethod<>(this::setString).execute("Banana");
        assertThat(s, is("Banana"));
    }

    public void setString(String s){
        this.s = s;
    }
}