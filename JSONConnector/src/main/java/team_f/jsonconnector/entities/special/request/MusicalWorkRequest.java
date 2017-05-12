package team_f.jsonconnector.entities.special.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import team_f.jsonconnector.entities.MusicalWork;
import team_f.jsonconnector.entities.Request;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicalWorkRequest extends Request<MusicalWork> {
}
