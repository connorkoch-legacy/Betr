package com.csci448.betr

data class Bet(var betText: String, var betCreator: User, var betAcceptor: User, var betAmmount: Double)