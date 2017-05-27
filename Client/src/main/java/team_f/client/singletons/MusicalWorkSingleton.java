package team_f.client.singletons;

import team_f.client.configuration.Configuration;
import team_f.client.helper.RequestResponseHelper;
import team_f.client.pages.PageAction;
import team_f.client.pages.musicalwork.MusicalWorkManagement;
import team_f.client.pages.musicalwork.MusicalWorkParameter;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.MusicalWork;
import team_f.jsonconnector.entities.Request;
import team_f.jsonconnector.entities.list.MusicalWorkList;
import team_f.jsonconnector.entities.special.errorlist.MusicalWorkErrorList;
import team_f.jsonconnector.enums.request.ActionType;

import java.net.MalformedURLException;
import java.net.URL;
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
                    if(value != null) {
                        Request request = new Request();
                        request.setActionType(ActionType.GET_ALL);

                        MusicalWorkList personList = (MusicalWorkList) RequestResponseHelper.writeAndReadJSONObject(getMusicalWorkURL(), request, MusicalWorkList.class);

                        if(personList != null) {
                            return personList.getMusicalWorkList();
                        }
                    }

                    return null;
                }
            });

            _musicalWork.setOnCreate(new PageAction<MusicalWorkErrorList, MusicalWork>() {
                @Override
                public MusicalWorkErrorList doAction(MusicalWork value) {
                    if(value != null) {
                        Request request = new Request();
                        request.setActionType(ActionType.CREATE);
                        request.setEntity(value);

                        MusicalWorkErrorList errorList = (MusicalWorkErrorList) RequestResponseHelper.writeAndReadJSONObject(getMusicalWorkURL(), request, MusicalWorkErrorList.class);
                        return errorList;
                    }

                    return null;
                }
            });

            _musicalWork.setOnDelete(new PageAction<MusicalWorkErrorList, MusicalWork>() {
                @Override
                public MusicalWorkErrorList doAction(MusicalWork value) {
                    if(value != null) {
                        Request request = new Request();
                        request.setActionType(ActionType.DELETE);
                        request.setEntity(value);

                        MusicalWorkErrorList errorList = (MusicalWorkErrorList) RequestResponseHelper.writeAndReadJSONObject(getMusicalWorkURL(), request, MusicalWorkErrorList.class);
                        return errorList;
                    }

                    return null;
                }
            });

            _musicalWork.setOnEdit(new PageAction<MusicalWorkErrorList, MusicalWork>() {
                @Override
                public MusicalWorkErrorList doAction(MusicalWork value) {
                    if(value != null) {
                        Request request = new Request();
                        request.setActionType(ActionType.UPDATE);
                        request.setEntity(value);

                        MusicalWorkErrorList errorList = (MusicalWorkErrorList) RequestResponseHelper.writeAndReadJSONObject(getMusicalWorkURL(), request, MusicalWorkErrorList.class);
                        return errorList;
                    }

                    return null;
                }
            });
        }

        return _musicalWork;
    }

    private static URL getMusicalWorkURL() {
        try {
            return new URL(new URL(_configuration.getRootURI()), URIList.musicalWork);
        } catch (MalformedURLException e) {
        }

        return null;
    }
}
