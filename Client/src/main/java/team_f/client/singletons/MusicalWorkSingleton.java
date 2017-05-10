package team_f.client.singletons;

import team_f.client.configuration.Configuration;
import team_f.client.pages.PageAction;
import team_f.client.pages.musicalwork.MusicalWorkManagement;
import team_f.client.pages.musicalwork.MusicalWorkParameter;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.MusicalWork;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MusicalWorkSingleton {
    private static MusicalWorkManagement _musicalWork;
    private static Configuration _configuration;

    private MusicalWorkSingleton() {
    }

    public static MusicalWorkManagement getInstance(Configuration configuration) {
        if (_musicalWork == null) {
            _configuration = configuration;
            _musicalWork = new MusicalWorkManagement();
            
            _musicalWork.setOnLoadList(new PageAction<List<MusicalWork>, MusicalWorkParameter>() {
                @Override
                public List<MusicalWork> doAction(MusicalWorkParameter value) {
                    /*Request requestObject = new Request();
                    ErrorList request = (ErrorList) RequestResponseHelper.writeAndReadJSONObject(getMusicalWorkURL(), requestObject, ErrorList.class);

                    boolean isSuccessful;

                    if (request != null && request.getKeyValueList() != null && request.getKeyValueList().size() == 0) {
                        isSuccessful = true;
                        // _table.getItems().add(person);
                    } else {
                        isSuccessful = false;
                    }*/

                    return new ArrayList<>();
                }
            });
        }

        return _musicalWork;
    }

    private static URL getMusicalWorkURL() {
        try {
            return new URL(new URL(_configuration.getStartURI()), URIList.musicalWork);
        } catch (MalformedURLException e) {
        }

        return null;
    }
}
