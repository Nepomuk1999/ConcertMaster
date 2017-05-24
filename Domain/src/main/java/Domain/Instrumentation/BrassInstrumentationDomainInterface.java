package Domain.Instrumentation;

/**
 * @author Julian
 */

public interface BrassInstrumentationDomainInterface extends BrassInstrumentationViewInterface {
    void setHorn(int horn);

    void setTrumpet(int trumpet);

    void setTrombone(int trombone);

    void setTube(int tube);
}
