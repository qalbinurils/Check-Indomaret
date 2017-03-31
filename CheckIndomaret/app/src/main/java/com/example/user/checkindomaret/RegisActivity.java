package com.example.user.checkindomaret;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mPhotoView;
    private ImageButton mPhotoButton;
    private TextView login;
    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private static String url = "http://10.20.0.108/indomaret/jsonregister.php";
    private static final String TAG_SUCCESS = "success";
    private EditText inputName, inputAddress, inputNo, inputUser, inputPass;
    private Button buttonRegis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        inputName = (EditText) findViewById(R.id.input_name);
        inputAddress = (EditText) findViewById(R.id.input_address);
        inputNo = (EditText) findViewById(R.id.input_mobile);
        inputUser = (EditText) findViewById(R.id.input_username);
        inputPass = (EditText) findViewById(R.id.input_pass);

        buttonRegis = (Button) findViewById(R.id.btn_signup);
        login = (TextView) findViewById(R.id.link_login);
        buttonRegis.setOnClickListener(this);
        login.setOnClickListener(this);

        mPhotoView = (ImageView) findViewById(R.id.mfoto);
        mPhotoButton = (ImageButton) findViewById(R.id.bfoto);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.link_login:
                Intent i = new Intent(this, LoginContentFragment.class);
                startActivity(i);
                break;
            case R.id.btn_signup:
                new Registration(inputName.getText().toString(),inputAddress.getText().toString(),
                        inputNo.getText().toString(),inputUser.getText().toString(),inputPass.getText().toString()).execute(url);
                break;
            default:
                break;
        }
    }

    private class Registration extends AsyncTask<String, String, String> {
        String rnama, raddress, rnohp, rusername, rpassword;

        public Registration(String rnama, String raddress, String rnohp, String rusername, String rpassword) {
            this.rnama = rnama;
            this.raddress = raddress;
            this.rnohp = rnohp;
            this.rusername = rusername;
            this.rpassword = rpassword;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisActivity.this);
            pDialog.setMessage("Creating Acount..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // Building Parameters
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("nama",rnama));
            param.add(new BasicNameValuePair("address", raddress));
            param.add(new BasicNameValuePair("no_hp",rnohp));
            param.add(new BasicNameValuePair("username", rusername));
            param.add(new BasicNameValuePair("password", rpassword));

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
                    Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisActivity.this, "gagal", Toast.LENGTH_SHORT).show();
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
