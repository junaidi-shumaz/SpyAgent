package com.example.shumazahamedjunaidi.agents;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import android.provider.Telephony;

import com.dao.AgentsDAO;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shumazahamedjunaidi on 8/15/17.
 */

public class SmsReceiver extends BroadcastReceiver {
    private String TAG = SmsReceiver.class.getSimpleName();

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the data (SMS data) bound to intent

        if (intent.getAction().equals(SMS_RECEIVED)) {

            Bundle bundle = intent.getExtras();

            SmsMessage[] msgs = null;

            String str = "";

            if (bundle != null) {
        /*        // Retrieve the SMS Messages received
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];

                // For every SMS message received
                String number = "";
                for (int i = 0; i < msgs.length; i++) {
                    // Convert Object array
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    // Sender's phone number
                    //str += "SMS from " + msgs[i].getOriginatingAddress() + " : ";
                    number = msgs[i].getDisplayOriginatingAddress();
                    // Fetch the text message
                    str += msgs[i].getMessageBody().toString();
                    // Newline 🙂
                    str += "\n";
                }

          */
              Object[] smsPdus = (Object[]) intent.getSerializableExtra("pdus");
                byte[] smsPdu = (byte[]) smsPdus[0];
                String smsFormat = (String) intent.getSerializableExtra("format");
                SmsMessage sms = SmsMessage.createFromPdu(smsPdu);
                String number = sms.getDisplayOriginatingAddress();
                String msg = sms.getMessageBody().toString();
               // Display the entire SMS Message
                Log.d(TAG, msg);
                AgentsDAO dataCon = new AgentsDAO(context);
                ArrayList<String> res = dataCon.smsMatchLookUp(number);
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                dataCon.dbinsertMSG(Integer.parseInt(res.get(0)),msg,number,"0",res.get(1),currentDateTimeString);
                Toast.makeText(context, "Message From Agent-" + res.get(1), Toast.LENGTH_SHORT).show();


            }
        }
    }
}