package com.example.seniorproject

class User {

    var name = ""
    var Email = ""
    var password = ""
    var score = ""

    constructor(name: String, Email: String, password: String, score: Int) {

        this.name = name
        this.Email = Email
        this.password = password
        this.score = score.toString()
    }

    override fun toString(): String {
        return "User(name='$name', Email='$Email', password='$password')"
    }


}