package com.example.seniorproject

import android.app.DatePickerDialog

class IotInfo {
    var temp = ""
    var humidity = ""
    var moisture = ""
    var Date = ""
    constructor(temp: String, moisture: String, humidity: String,Date:String) {
        this.temp = temp
        this.moisture = moisture
        this.humidity = humidity
        this.Date = Date
    }


}