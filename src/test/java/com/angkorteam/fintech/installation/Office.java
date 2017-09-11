package com.angkorteam.fintech.installation;

public enum Office {

    Office01("ការិយាល�?យ �?�?�?្�?បន្ទាយមានជ�?យ", ""),
    Office02("ការិយាល�?យ �?�?�?្�?បា�?់ដំបង", ""),
    Office03("ការិយាល�?យ �?�?�?្�?កំពង់ចាម", ""),
    Office04("ការិយាល�?យ �?�?�?្�?កំពង់ឆ្នាំង", ""),
    Office05("ការិយាល�?យ �?�?�?្�?កំពង់ស្ពឺ", ""),
    Office06("ការិយាល�?យ �?�?�?្�?កំពង់ធំ", ""),
    Office07("ការិយាល�?យ �?�?�?្�?កំព�?", ""),
    Office08("ការិយាល�?យ �?�?�?្�?កណ្�?ាល", ""),
    Office09("ការិយាល�?យ �?�?�?្�?កោះកុង", ""),
    Office10("ការិយាល�?យ �?�?�?្�?ក្រច�?ះ", ""),
    Office11("ការិយាល�?យ �?�?�?្�?មណ្ឌលគីរី", ""),
    Office12("ការិយាល�?យ �?�?�?្�?ឧ�?្�?រមានជ�?យ", ""),
    Office13("ការិយាល�?យ �?�?�?្�?ព្រះវិហារ", ""),
    Office14("ការិយាល�?យ �?�?�?្�?ពោធិ�?សា�?់", ""),
    Office15("ការិយាល�?យ �?�?�?្�?ព្រៃវែង", ""),
    Office16("ការិយាល�?យ �?�?�?្�?រ�?នគីរី", ""),
    Office17("ការិយាល�?យ �?�?�?្�?សៀមរាប", ""),
    Office18("ការិយាល�?យ �?�?�?្�?ស្ទឹង�?្រែង", ""),
    Office19("ការិយាល�?យ �?�?�?្�?ស្វាយរៀង", ""),
    Office20("ការិយាល�?យ �?�?�?្�?�?ាកែវ", ""),
    Office21("ការិយាល�?យ ក្រុងកែប", ""),
    Office22("ការិយាល�?យ �?�?�?្�?បៃលិន", ""),
    Office23("ការិយាល�?យ ក្រុងភ្នំព�?ញ", ""),
    Office24("ការិយាល�?យ �?�?�?្�?កំពង់សោម", ""),
    Office25("ការិយាល�?យ �?�?�?្�?�?្បូងឃ្មុំ", "");

    private String name;
    private String description;

    Office(String name, String description) {
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
