package com.annikadietz.shoppy_shoppingbuddy

import com.google.gson.JsonObject
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class GoogleDirectionsService : GoogleDirectionsAPI{
    override fun getDirections(url: String): JSONObject {
        var urlConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var result:String = ""

        val url = URL(url)
        urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.setRequestMethod("GET")
        urlConnection.connect()
        val inputStream: InputStream = urlConnection.getInputStream()
        val buffer = StringBuffer()
        if (inputStream == null) {
            return JSONObject()
        }
        reader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = reader.readLine()
        while (line != null) {
            buffer.append(line + "\n");
            line = reader.readLine()
        }
        if (buffer.length == 0) {
            return JSONObject()
        }
        result = buffer.toString()
        return JSONObject(result)
    }
}