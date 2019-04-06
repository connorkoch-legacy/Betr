package com.csci448.betr

data class User (var userName: String, var
passWord: String, var friendList: MutableList<User>?, var betList: MutableList<Bet>?)