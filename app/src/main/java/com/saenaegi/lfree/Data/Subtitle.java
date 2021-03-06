package com.saenaegi.lfree.Data;

public class Subtitle {

    private String idgoogle;
    private String name;
    private int recommend;
    private String sectionF;
    private String sectionS;
    private boolean type;

    public Subtitle(){

    }

    public Subtitle(String idgoogle, String name, int recommend, String sectionF, String sectionS, int sectionNum, boolean type){
        this.idgoogle=idgoogle;
        this.name=name;
        this.recommend=recommend;
        this.sectionF=sectionF;
        this.sectionS=sectionS;
        this.type=type;

    }

    public String getIdgoogle() {
        return idgoogle;
    }

    public String getName() {
        return name;
    }

    public int getRecommend() {
        return recommend;
    }

    public String getSectionF() {
        return sectionF;
    }

    public String getSectionS() {
        return sectionS;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public void setIdgoogle(String idgoogle) {
        this.idgoogle = idgoogle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public void setSectionF(String sectionF) {
        this.sectionF = sectionF;
    }

    public void setSectionS(String sectionS) {
        this.sectionS = sectionS;
    }

}
