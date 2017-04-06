package database;

/**
 * Created by Home on 06.04.2017.
 */
public class MusicalWorkEntity {
    private int musicalWorkId;
    private int instrumentationId;
    private String name;
    private String composer;

    public int getMusicalWorkId() {
        return musicalWorkId;
    }

    public void setMusicalWorkId(int musicalWorkId) {
        this.musicalWorkId = musicalWorkId;
    }

    public int getInstrumentationId() {
        return instrumentationId;
    }

    public void setInstrumentationId(int instrumentationId) {
        this.instrumentationId = instrumentationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicalWorkEntity that = (MusicalWorkEntity) o;

        if (musicalWorkId != that.musicalWorkId) return false;
        if (instrumentationId != that.instrumentationId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (composer != null ? !composer.equals(that.composer) : that.composer != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = musicalWorkId;
        result = 31 * result + instrumentationId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (composer != null ? composer.hashCode() : 0);
        return result;
    }
}
