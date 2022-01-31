package com.example.bajaj_firatapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log


class SmsReceiver : BroadcastReceiver() {
    var TAG = SmsReceiver::class.java.simpleName
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Toast.makeText(context,"sms from bajaj",Toast.LENGTH_SHORT).show()

        val bundle = intent.extras
        if (bundle != null) {
            val pdus = bundle["pdus"] as Array<Any>?
            val messages: Array<SmsMessage?> = arrayOfNulls<SmsMessage>(pdus!!.size)
            for (i in pdus!!.indices) {
                messages[i] = SmsMessage.createFromPdu(pdus!![i] as ByteArray)
            }
            if (messages.size > -1) {
                Log.i(TAG, "Message recieved: " + messages[0]?.getMessageBody())
                Toast.makeText(context, messages[0]?.getMessageBody(),Toast.LENGTH_SHORT).show()

            }
        }
    }
}