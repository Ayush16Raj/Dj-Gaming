package com.example.djgaming


import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class MatchData(val matchDate: String? = null, val matchTime: String? = null, val referenceId: String? = null, val entryFee: String? = null, val poolPrize: String? = null, val slots: String?= null,val imgThumbnail: String?=null,val category:String?=null,val tournamentRoomId: String?=null,val tournamentRoomPassword: String?= null){

}
