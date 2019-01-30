package models

import sun.security.jgss.GSSToken.readInt
import java.sql.SQLData
import java.sql.SQLInput
import java.sql.SQLOutput


data class State(var name: String, var region: String, var largestCity: String, var capital: String, var population: Int)

data class City(val cityName: String, val stateName: String, var population: Int)