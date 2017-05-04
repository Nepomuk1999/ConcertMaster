package team_f.client.pages.musicalwork;

/**
 * Created by dominik on 04.05.17.
 */
public class MusicalWorkTestData {
    private Integer _id;
    private String _musicalWorkName;
    private String _composer;
    private String _instrumentation;

    public MusicalWorkTestData(Integer _id, String _musicalWorkName, String _composer, String _instrumentation) {
        this._id = _id;
        this._musicalWorkName = _musicalWorkName;
        this._composer = _composer;
        this._instrumentation = _instrumentation;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String get_musicalWorkName() {
        return _musicalWorkName;
    }

    public void set_musicalWorkName(String _musicalWorkName) {
        this._musicalWorkName = _musicalWorkName;
    }

    public String get_composer() {
        return _composer;
    }

    public void set_composer(String _composer) {
        this._composer = _composer;
    }

    public String get_instrumentation() {
        return _instrumentation;
    }

    public void set_instrumentation(String _instrumentation) {
        this._instrumentation = _instrumentation;
    }
}
