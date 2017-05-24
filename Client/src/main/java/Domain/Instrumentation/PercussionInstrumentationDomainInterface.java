package Domain.Instrumentation;

/**
 * @author Julian
 */
public interface PercussionInstrumentationDomainInterface extends PercussionInstrumentationViewInterface {
    void setKettledrum(int kettledrum);

    void setPercussion(int percussion);

    void setHarp(int harp);

    void setPercussionDescription(String description);
}
