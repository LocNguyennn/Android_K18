package com.example.android_w1

class DataStore private constructor(var fullName : String, var email: String, var password: String){
    companion object{
        private var instance : User? = null
        operator fun invoke(fullName : String, email: String, password: String):User = synchronized(this){
            if(instance == null || (!fullName.equals("") && !email.equals("") && !password.equals("")))
                instance = User(fullName,email,password)
            return instance as User
        }
    }
}
