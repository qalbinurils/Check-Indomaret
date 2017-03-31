package com.example.user.checkindomaret;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qalbins on 04/02/2017.
 */

public class LoginContentFragment extends Fragment implements View.OnClickListener {
    private Button mLogin;
    private TextView mRegis;
    private EditText mUser, mPass;
    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private static String url = "http://10.20.0.108/indomaret/jsonloginattemp.php";
    private static final String TAG_SUCCESS = "success";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_login, container, false);
        mRegis = (TextView) v.findViewById(R.id.link_signup);
        mRegis.setOnClickListener(this);
        mLogin = (Button) v.findViewById(R.id.btn_login);
        mLogin.setOnClickListener(this);

        mUser = (EditText) v.findViewById(R.id.input_user);
        mPass = (EditText) v.findViewById(R.id.input_password);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                new Login(mUser.getText().toString(), mPass.getText().toString()).execute();
                break;
            case R.id.link_signup:
                Intent intent = new Intent(getActivity(), RegisActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private class Login extends AsyncTask<String, String, String> {
        String musername, mpassword;

        public Login(String musername, String mpassword) {
            this.musername = musername;
            this.mpassword = mpassword;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // Building Parameters
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("username", musername));
            param.add(new BasicNameValuePair("password", mpassword));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", param);


            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Berhasil Login", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), UserInActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    });

                    // closing this screen
                } else {
                    // failed to create product
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Username atau Password salah", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }
}
