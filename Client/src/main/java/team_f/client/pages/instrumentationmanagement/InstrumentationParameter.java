package team_f.client.pages.instrumentationmanagement;

import team_f.jsonconnector.entities.Instrumentation;

public class InstrumentationParameter {
    private Instrumentation _instrumentation;

    public Instrumentation getInstrumentation(){
        return _instrumentation;
    }

    public void setInstrumentation(Instrumentation instrumentation){
        _instrumentation = instrumentation;
    }
}
