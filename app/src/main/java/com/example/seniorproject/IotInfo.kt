package com.example.seniorproject

class IotInfo {
    var temp = ""
    var humidity = ""
    var moisture = ""
    var currentTimelimit = ""
    constructor(temp: String, moisture: String, humidity: String,currentTimelimit:String) {
        this.temp = temp
        this.moisture = moisture
        this.humidity = humidity
        this.currentTimelimit = currentTimelimit
    }


}