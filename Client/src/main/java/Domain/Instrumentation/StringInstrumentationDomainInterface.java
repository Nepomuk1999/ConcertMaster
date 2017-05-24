package Domain.Instrumentation;

/**
 * @author Julian
 */

public interface StringInstrumentationDomainInterface extends StringInstrumentationViewInterface {
    void setViolin1(int violin1);

    void setViolin2(int violin2);

    void setViola(int viola);

    void setViolincello(int violincello);

    void setDoublebass(int doublebass);
}
