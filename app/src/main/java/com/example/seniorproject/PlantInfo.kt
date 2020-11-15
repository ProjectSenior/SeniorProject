package com.example.seniorproject

class PlantInfo {
    var temp = 0
    var plantName = ""
    var date = ""
    var status = 0
    var anno = ""
    var imageUrl = ""

    constructor(plantName: String, date: String, anno: String, imageUrl: String) {
        this.plantName = plantName
        this.anno = anno
        this.date = date
        this.imageUrl = imageUrl

    }

}