package com.example.trackpad

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
class data {

    private val client = OkHttpClient()
    private val request = Request.Builder().url("ws://192.168.1.34:8765/ws").build()
    private val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            super.onOpen(webSocket, response)
            println("Connected to WebSocket")
        }
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            println("Message from server: $text")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            super.onFailure(webSocket, t, response)
            println("Error: ${t.message}")
        }


    }
    val webSocket = client.newWebSocket(request, webSocketListener)
}

fun sendTouchData(webSocket: WebSocket, x: Float, y: Float, eventType: String) {
    val touchData = JSONObject()
    touchData.put("type", eventType)
    touchData.put("x", x)
    touchData.put("y", y)
    webSocket.send(touchData.toString())
}