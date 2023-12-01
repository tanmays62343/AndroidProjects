package com.trx.models

data class Message(
    var message : String,
    var sendersID : String
){
    constructor() : this("","")
}
