package se.bbb.jspecs;


public class SpecificationHelper<T> implements SpecificationSupplier{

    private T currentTestClass;


    public SpecificationHelper(T currentTestClass){
        this.currentTestClass = currentTestClass;
    }

    @Override
    public String getSpecificationExample() {
        throw new java.lang.NoSuchMethodError();
    }
}
