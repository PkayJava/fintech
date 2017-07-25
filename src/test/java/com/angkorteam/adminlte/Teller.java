package com.angkorteam.adminlte;

import com.angkorteam.fintech.dto.TellerState;

public enum Teller { 

    TA001_Office01("Alberto Giubilini", Office.Office01, TellerState.Active),
    TI002_Office01("Allen Buchanan", Office.Office01, TellerState.Inactive),
    TA003_Office02("Anders Sandberg", Office.Office02, TellerState.Active),
    TI004_Office02("Andreas Kappes", Office.Office02, TellerState.Inactive),
    TA005_Office03("Barbara Sahakian", Office.Office03, TellerState.Active),
    TI006_Office03("Bart Gremmen", Office.Office03, TellerState.Inactive),
    TA007_Office04("Bill Fulford", Office.Office04, TellerState.Active),
    TI008_Office04("Brian Earp", Office.Office04, TellerState.Inactive),
    TA009_Office05("Carsten de Dreu", Office.Office05, TellerState.Active),
    TI010_Office05("Christine Korsgaard", Office.Office05, TellerState.Inactive),
    TA011_Office06("Christopher Gyngell", Office.Office06, TellerState.Active),
    TI012_Office06("Clare Heyward", Office.Office06, TellerState.Inactive),
    TA013_Office07("David Edmonds", Office.Office07, TellerState.Active),
    TI014_Office07("Deborah Sheehan", Office.Office07, TellerState.Inactive),
    TA015_Office08("Derek Bolton", Office.Office08, TellerState.Active),
    TI016_Office08("Dominic Wilkinson", Office.Office08, TellerState.Inactive),
    TA017_Office09("Frances Kamm", Office.Office09, TellerState.Active),
    TI018_Office09("Guy Kahane", Office.Office09, TellerState.Inactive),
    TA019_Office10("Hannah Maslen", Office.Office10, TellerState.Active),
    TI020_Office10("Henry Shue", Office.Office10, TellerState.Inactive),
    TA021_Office11("Ichinose Masaki", Office.Office11, TellerState.Active),
    TI022_Office11("Ilina Singh", Office.Office11, TellerState.Inactive),
    TA023_Office12("Ingmar Persson", Office.Office12, TellerState.Active),
    TI024_Office12("James Griffin", Office.Office12, TellerState.Inactive),
    TA025_Office13("Janet Radcliffe Richards", Office.Office13, TellerState.Active),
    TI026_Office13("John Broome", Office.Office13, TellerState.Inactive),
    TA027_Office14("John Tasioulas", Office.Office14, TellerState.Active),
    TI028_Office14("Jonathan Glover", Office.Office14, TellerState.Inactive),
    TA029_Office15("Jonathan Pugh", Office.Office15, TellerState.Active),
    TI030_Office15("Joshua Shepherd", Office.Office15, TellerState.Inactive),
    TA031_Office16("Julian Savulescu", Office.Office16, TellerState.Active),
    TI032_Office16("Katrien Devolder", Office.Office16, TellerState.Inactive),
    TA033_Office17("Kei Hiruta", Office.Office17, TellerState.Active),
    TI034_Office17("Luciano Floridi", Office.Office17, TellerState.Inactive),
    TA035_Office18("Mark Sheehan", Office.Office18, TellerState.Active),
    TI036_Office18("Michael Robillard", Office.Office18, TellerState.Inactive),
    TA037_Office19("Miriam Wood", Office.Office19, TellerState.Active),
    TI038_Office19("Nadira Faber", Office.Office19, TellerState.Inactive),
    TA039_Office20("Neil Levy", Office.Office20, TellerState.Active),
    TI040_Office20("Nicholas Shackel", Office.Office20, TellerState.Inactive),
    TA041_Office21("Pak-Hang Wong", Office.Office21, TellerState.Active),
    TI042_Office21("Paul van Lange", Office.Office21, TellerState.Inactive),
    TA043_Office22("Peter Singer", Office.Office22, TellerState.Active),
    TI044_Office22("Philip Pettit", Office.Office22, TellerState.Inactive),
    TA045_Office23("Rachel Gaminiratne", Office.Office23, TellerState.Active),
    TI046_Office23("Rebecca Brown", Office.Office23, TellerState.Inactive),
    TA047_Office24("Rocci Wilkinson", Office.Office24, TellerState.Active),
    TI048_Office24("Roger Crisp", Office.Office24, TellerState.Inactive),
    TA049_Office25("Samuel Scheffler", Office.Office25, TellerState.Active),
    TI050_Office25("Shelly Kagan", Office.Office25, TellerState.Inactive);

    private String name;

    private Office office;

    private TellerState status;

    private Teller(String name, Office office, TellerState status) {
        this.name = name;
        this.office = office;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Office getOffice() {
        return office;
    }

    public TellerState getStatus() {
        return status;
    }

}
