package team_f.client.singletons;

import team_f.client.configuration.Configuration;
import team_f.client.helper.RequestResponseHelper;
import team_f.client.pages.PageAction;
import team_f.client.pages.musicianmanagement.MusicianManagement;
import team_f.client.pages.musicianmanagement.MusicianTableHelper;
import team_f.client.pages.musicianmanagement.PersonParameter;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.ErrorList;
import team_f.jsonconnector.entities.Person;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MusiciansTableSingleton {
    private static MusicianManagement _musicianTable;
    private static Configuration _configuration;

    private MusiciansTableSingleton() {
    }

    public static MusicianManagement getInstance(Configuration configuration) {
        if (_musicianTable == null) {
            _configuration = configuration;
            _musicianTable = new MusicianManagement();

            _musicianTable.setOnCreate(new PageAction<Person, Person>() {
                @Override
                public Person doAction(Person value) {
                    return null;
                }
            });

            _musicianTable.setOnLoadList(new PageAction<List<Person>, PersonParameter>() {
                @Override
                public List<Person> doAction(PersonParameter value) {
                    if(value != null) {
                        // @TODO: get the list
                        return MusicianTableHelper.getPersonList();
                    }

                    return null;
                }
            });

            _musicianTable.setOnCreate(new PageAction<Person, Person>() {
                @Override
                public Person doAction(Person value) {
                    if(value != null) {
                        ErrorList request = (ErrorList) RequestResponseHelper.writeAndReadJSONObject(getRegisterURL(), value, ErrorList.class);

                        boolean isSuccessful;

                        if (request != null && request.getKeyValueList() != null && request.getKeyValueList().size() == 0) {
                            isSuccessful = true;
                            // @TODO: set the ID
                        } else {
                            isSuccessful = false;
                            // @TODO: show a error messge
                        }

                        return value;
                    }

                    return null;
                }
            });
        }

        return _musicianTable;
    }

    private static URL getRegisterURL() {
        try {
            return new URL(new URL(_configuration.getStartURI()), URIList.register);
        } catch (MalformedURLException e) {
        }

        return null;
    }
}


