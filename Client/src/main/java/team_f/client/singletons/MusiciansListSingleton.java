package team_f.client.singletons;

import team_f.application.PersonApplication;
import team_f.client.configuration.Configuration;
import team_f.client.helper.converter.PersonConverter;
import team_f.client.pages.PageAction;
import team_f.client.pages.musicianmanagement.MusiciansList;
import team_f.client.pages.musicianmanagement.PersonParameter;
import team_f.jsonconnector.entities.*;
import java.util.LinkedList;
import java.util.List;

public class MusiciansListSingleton {
    private static MusiciansList _musiciansList;
    private static PersonApplication _facade = PersonApplication.getInstance();
    private static Configuration _configuration;

    private MusiciansListSingleton() {
    }

    public static MusiciansList getInstance(Configuration configuration) {
        if (_musiciansList == null) {
            _configuration = configuration;
            _musiciansList = new MusiciansList();

            _musiciansList.setOnLoadList(new PageAction<List<Person>, PersonParameter>() {
                @Override
                public List<Person> doAction(PersonParameter value) {
                    if(value != null) {
                        List<team_f.domain.entities.Person> personEntityList = _facade.getList();
                        List<team_f.jsonconnector.entities.Person> personList = new LinkedList<>();
                        team_f.jsonconnector.entities.Person person;

                        for(team_f.domain.entities.Person item : personEntityList) {
                            person = PersonConverter.convertToJSON(item);
                            personList.add(person);
                        }

                        return personList;
                    }

                    return null;
                }
            });
        }

        return _musiciansList;
    }
}
