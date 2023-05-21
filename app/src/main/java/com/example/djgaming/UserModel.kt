package com.example.djgaming

  class UserModel {
     var email: String? = null
     var fullName: String? = null
      var userid:String? = null
      var userType:String?=null


      constructor() {

      }

     constructor(email: String, fullName: String,userid:String,userType:String) {
         this.email = email
         this.fullName = fullName
         this.userid = userid
         this.userType = userType

     }

 }

