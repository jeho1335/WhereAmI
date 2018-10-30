package com.jhmk.whereami.Module.Custom.Dialog.Empty

class Test {
    fun networkCall(isSuccess : (String) -> Unit, onError : (Throwable) -> Unit){
        try{
            isSuccess("Success")
        }catch (e :Exception){
            isSuccess("Failed")
        }
    }
}