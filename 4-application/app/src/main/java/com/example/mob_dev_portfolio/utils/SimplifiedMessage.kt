package com.example.mob_dev_portfolio.utils

import org.json.JSONObject
import org.json.JSONException

object SimplifiedMessage {
    fun get(stringMessage: String): HashMap<String, String>{
        val messages = HashMap<String, String>()
        val jsonObject = JSONObject(stringMessage)

        try {
            val jsonMessages = jsonObject.getJSONObject("message")
            jsonMessages.keys().forEach{messages[it] = jsonMessages.getString(it)}

        }catch (e: JSONException){
            messages["messages"] = jsonObject.getString("messages")

        }
        return messages
    }
}