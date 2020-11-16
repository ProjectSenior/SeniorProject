package com.example.seniorproject

class PlantInfo {
    var temp = ""
    var humidity = ""
    var moisture = ""
    var plantName = ""
    var date = ""
    var anno = ""
    var imageUrl = ""

    constructor(plantName: String, date: String, anno: String, imageUrl: String,temp:String,humidity:String,moisture:String) {
        this.plantName = plantName
        this.anno = anno
        this.date = date
        this.imageUrl = imageUrl
        this.temp = temp
        this.humidity = humidity
        this.moisture = moisture

    }

}