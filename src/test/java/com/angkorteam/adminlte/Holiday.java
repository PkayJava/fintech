package com.angkorteam.adminlte;

public enum Holiday {

    Jan01("New Year's Day", "01 Jan", "01 Jan", false),
    Jan02("Victory Day", "07 Jan", "07 Jan", false),
    MeakBochea("Meak Bochea", null, null, true),
    Mar01("International Woman's Day", "08 Mar", "08 Mar", false),
    Apr01("Khmer New Year", "14 Apr", "16 Apr", false),
    VisakBochea("Visak Bochea", null, null, true),
    May01("Labor Day", "01 May", "01 May", false),
    ChhorkPreahNangkorl("Royal Ploughing Ceremony", null, null, true),
    May03("King Sihamoni's Birthday", "13 May", "15 May", false),
    Jun01("International Children's Day", "01 Jun", "01 Jun", false),
    Jun02("Queen Mother's Birthday", "08 Jun", "08 Jun", false),
    Sep01("Constitution Day", "24 Sep", "24 Sep", false),
    PchumBen("Pchum Ben Festival", null, null, true),
    Oct01("King Father's Commemoration Day", "15 Oct", "15 Oct", false),
    Oct02("Paris Peace Agreement Day", "23 Oct", "23 Oct", false),
    WaterFestival("Water Festival", null, null, true),
    Nov01("Independence Day", "09 Nov", "09 Nov", false),
    Dec01("Human Rights Day", "10 Dec", "10 Dec", false);

    private String name;

    private String startDate;

    private String endDate;

    private boolean movable;

    Holiday(String name, String startDate, String endDate, boolean movable) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.movable = movable;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isMovable() {
        return movable;
    }

}
