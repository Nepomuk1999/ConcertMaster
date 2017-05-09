package team_f.client.singletons;

import team_f.client.configuration.Configuration;
import team_f.client.pages.musicalwork.MusicalWorkManagement;
import team_f.jsonconnector.common.URIList;
import java.net.MalformedURLException;
import java.net.URL;

public class MusicalWorkSingleton {
    private static MusicalWorkManagement _musicalWork;
    private static Configuration _configuration;

    private MusicalWorkSingleton() {
    }

    public static MusicalWorkManagement getInstance(Configuration configuration) {
        if (_musicalWork == null) {
            _configuration = configuration;
            _musicalWork = new MusicalWorkManagement();

            // @TODO: implement
            /*_musicalWork.setOnCreate(new PageAction<MusicalWork, MusicalWork>() {
                @Override
                public MusicalWork doAction(MusicalWork value) {
                    ErrorList request = (ErrorList) RequestResponseHelper.writeAndReadJSONObject(getMusicalWorkURL(), value, ErrorList.class);

                    boolean isSuccessful;

                    if (request != null && request.getKeyValueList() != null && request.getKeyValueList().size() == 0) {
                        isSuccessful = true;
                        // _table.getItems().add(person);
                    } else {
                        isSuccessful = false;
                        // @TODO: show error message
                    }

                    return new MusicalWork();
                }
            });*/
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
