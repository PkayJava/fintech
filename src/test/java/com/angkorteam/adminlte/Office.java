package com.angkorteam.adminlte;

public enum Office {

    Office01("ការិយាល័យ ខេត្តបន្ទាយមានជ័យ", ""), Office02("ការិយាល័យ ខេត្តបាត់ដំបង", ""), Office03("ការិយាល័យ ខេត្តកំពង់ចាម", ""), Office04("ការិយាល័យ ខេត្តកំពង់ឆ្នាំង", ""), Office05("ការិយាល័យ ខេត្តកំពង់ស្ពឺ", ""), Office06("ការិយាល័យ ខេត្តកំពង់ធំ", ""), Office07("ការិយាល័យ ខេត្តកំពត", ""), Office08("ការិយាល័យ ខេត្តកណ្តាល", ""), Office09("ការិយាល័យ ខេត្តកោះកុង", ""), Office10("ការិយាល័យ ខេត្តក្រចេះ", ""), Office11("ការិយាល័យ ខេត្តមណ្ឌលគីរី", ""), Office12("ការិយាល័យ ខេត្តឧត្តរមានជ័យ", ""), Office13("ការិយាល័យ ខេត្តព្រះវិហារ", ""), Office14("ការិយាល័យ ខេត្តពោធិ៍សាត់", ""), Office15("ការិយាល័យ ខេត្តព្រៃវែង", ""), Office16("ការិយាល័យ ខេត្តរតនគីរី", ""), Office17("ការិយាល័យ ខេត្តសៀមរាប", ""), Office18("ការិយាល័យ ខេត្តស្ទឹងត្រែង", ""), Office19("ការិយាល័យ ខេត្តស្វាយរៀង", ""), Office20("ការិយាល័យ ខេត្តតាកែវ", ""), Office21("ការិយាល័យ ក្រុងកែប", ""), Office22("ការិយាល័យ ខេត្តបៃលិន", ""), Office23("ការិយាល័យ ក្រុងភ្នំពេញ", ""), Office24("ការិយាល័យ ខេត្តកំពង់សោម", ""), Office25("ការិយាល័យ ខេត្តត្បូងឃ្មុំ", "");

    private String name;
    private String description;

    private Office(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
