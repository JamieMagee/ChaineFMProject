package uk.co.jamiemagee.chainefm.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.jamiemagee.chainefm.R;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_fragment, container, false);

        assert v != null;
        TextView title = (TextView)v.findViewById(R.id.textView);
        TextView aboutme = (TextView)v.findViewById(R.id.textView3);
        TextView aboutapp = (TextView)v.findViewById(R.id.textView4);

        aboutme.setOnClickListener(myButtonListener);
        aboutapp.setOnClickListener(myButtonListener);
        title.setOnClickListener(myButtonListener);


        return v;
    }

    private View.OnClickListener myButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = "http://google.com";
            switch(v.getId()){
                case R.id.textView :
                    url = "http://chainefm.com";
                    break;
                case R.id.textView3 :
                    url = "http://jamiemagee.co.uk";
                    break;
                case R.id.textView4 :
                    url = "https://github.com/JamieMagee/ChaineFMProject";
                    break;
            }
            Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            startActivity(browserIntent);
        }
    };
}
