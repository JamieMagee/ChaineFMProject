package uk.co.jamiemagee.chainefm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import uk.co.jamiemagee.chainefm.R;

/**
 * Created by jamagee on 07/08/13.
 */
public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_fragment, container, false);

        assert v != null;
        Button send = (Button)v.findViewById(R.id.send);

        send.setOnClickListener(myButtonListener);

        return v;
    }

    private View.OnClickListener myButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v = v.getRootView();
            assert v != null;
            RadioGroup sendby = (RadioGroup)v.findViewById(R.id.radioGroup);
            EditText textBox = (EditText)v.findViewById(R.id.message);
            String message;
            if (!textBox.getText().toString().equals("")) {
                message = textBox.getText().toString();
                switch(sendby.getCheckedRadioButtonId()){
                    case R.id.sms:
                        String preMessage = getString(R.string.pre_message);
                        message = preMessage + " " + message;
                        SmsManager smsManager = SmsManager.getDefault();
                        ArrayList<String> parts = smsManager.divideMessage(message);
                        String phoneNumber = getString(R.string.phone);
                        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
                        sendby.clearCheck();
                        textBox.setText(null);
                        break;
                    case R.id.email:
                        String emailAddress[] = {getString(R.string.email)};
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.setType("message/rfc822");
                        email.putExtra(Intent.EXTRA_EMAIL, emailAddress);
                        //email.putExtra(Intent.EXTRA_SUBJECT,"");
                        email.putExtra(Intent.EXTRA_TEXT, message);
                        sendby.clearCheck();
                        textBox.setText(null);
                        startActivity(email);
                        break;
                    default:
                        Toast.makeText(getActivity(), R.string.select_method, Toast.LENGTH_LONG).show();
                        break;
                }
            }
            else {
                Toast.makeText(getActivity(), R.string.no_message, Toast.LENGTH_LONG).show();
            }
        }
    };



}
