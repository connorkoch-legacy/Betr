package com.csci448.betr

class User {

    var userName: String = ""
    var passWord: String = ""
    var friendList: MutableList<User> = mutableListOf()
    var betList: MutableList<Bet> = mutableListOf()

}