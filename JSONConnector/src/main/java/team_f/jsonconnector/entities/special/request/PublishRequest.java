package team_f.jsonconnector.entities.special.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import team_f.jsonconnector.entities.Publish;
import team_f.jsonconnector.entities.Request;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishRequest extends Request<Publish> {
}
