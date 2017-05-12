package team_f.jsonconnector.entities.special.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.Request;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentationRequest extends Request<Instrumentation> {
}
