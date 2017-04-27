package team_f.domain.entities;

import team_f.domain.helper.TextHelper;
import team_f.domain.interfaces.DomainEntity;

public class Instrumentation implements DomainEntity {
    private int instrumentationID;
    //WoodInstrumentation
    private int flute;
    private int oboe;
    private int clarinet;
    private int bassoon;

    //StringInstrumentation
    private int violin1;
    private int violin2;
    private int viola;
    private int violincello;
    private int doublebass;

    //BrassInstrumentation
    private int horn;
    private int trumpet;
    private int trombone;
    private int tube;

    //PercussionInstrumentation
    private int kettledrum;
    private int percussion;
    private int harp;

    public int getInstrumentationID() {
        return instrumentationID;
    }

    public void setInstrumentationID(int instrumentationID) {
        this.instrumentationID = instrumentationID;
    }

    public int getFlute() {
        return flute;
    }

    public void setFlute(int flute) {
        this.flute = flute;
    }

    public int getOboe() {
        return oboe;
    }

    public void setOboe(int oboe) {
        this.oboe = oboe;
    }

    public int getClarinet() {
        return clarinet;
    }

    public void setClarinet(int clarinet) {
        this.clarinet = clarinet;
    }

    public int getBassoon() {
        return bassoon;
    }

    public void setBassoon(int bassoon) {
        this.bassoon = bassoon;
    }

    public int getViolin1() {
        return violin1;
    }

    public void setViolin1(int violin1) {
        this.violin1 = violin1;
    }

    public int getViolin2() {
        return violin2;
    }

    public void setViolin2(int violin2) {
        this.violin2 = violin2;
    }

    public int getViola() {
        return viola;
    }

    public void setViola(int viola) {
        this.viola = viola;
    }

    public int getViolincello() {
        return violincello;
    }

    public void setViolincello(int violincello) {
        this.violincello = violincello;
    }

    public int getDoublebass() {
        return doublebass;
    }

    public void setDoublebass(int doublebass) {
        this.doublebass = doublebass;
    }

    public int getHorn() {
        return horn;
    }

    public void setHorn(int horn) {
        this.horn = horn;
    }

    public int getTrumpet() {
        return trumpet;
    }

    public void setTrumpet(int trumpet) {
        this.trumpet = trumpet;
    }

    public int getTrombone() {
        return trombone;
    }

    public void setTrombone(int trombone) {
        this.trombone = trombone;
    }

    public int getTube() {
        return tube;
    }

    public void setTube(int tube) {
        this.tube = tube;
    }

    public int getKettledrum() {
        return kettledrum;
    }

    public void setKettledrum(int kettledrum) {
        this.kettledrum = kettledrum;
    }

    public int getPercussion() {
        return percussion;
    }

    public void setPercussion(int percussion) {
        this.percussion = percussion;
    }

    public int getHarp() {
        return harp;
    }

    public void setHarp(int harp) {
        this.harp = harp;
    }

    public String getWoodInstrumentationText() {
        return "Wood: " + TextHelper.getSeparatedText('/', flute, oboe, clarinet, bassoon);
    }

    public String getStringInstrumentationText() {
        return "String: " + TextHelper.getSeparatedText('/', getViolin1(), getViolin2(), getViola(), getViolincello(), getDoublebass());
    }

    public String getBrassInstrumentiaton() {
        return "Brass: " + TextHelper.getSeparatedText('/', getHorn(), getTrumpet(), getTrombone(), getTube());
    }

    public String getPercussionInstrumentation() {
        return "Percussion: " + TextHelper.getSeparatedText('/', getKettledrum(), getPercussion(), getHarp());
    }

    public String getInstrumentationText() {
        StringBuilder text = new StringBuilder();
        text.append(getWoodInstrumentationText());
        text.append('\n');
        text.append(getStringInstrumentationText());
        text.append('\n');
        text.append(getBrassInstrumentiaton());
        text.append('\n');
        text.append(getPercussionInstrumentation());

        return text.toString();
    }
}
