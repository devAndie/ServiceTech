package com.example.servicetech;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaymentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_stripe, container, false);


    }

/*        Button submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getContext(), HomeActivity.class);
                startActivity(home);
            }
        });

    }
    public void submitCard(View view) {
        // TODO: replace with your own test key
        TextView cardNumberField = (TextView) view.findViewById(R.id.cardNumber);
        TextView monthField = (TextView) view.findViewById(R.id.month);
        TextView yearField = (TextView) view.findViewById(R.id.year);
        TextView cvcField = (TextView) view.findViewById(R.id.cvc);

        card = new Card(
                cardNumberField.getText().toString(),
                Integer.valueOf(monthField.getText().toString()),
                Integer.valueOf(yearField.getText().toString()),
                cvcField.getText().toString()
        );

        card.setCurrency("usd");
        card.setName("[NAME_SURNAME]");
        card.setAddressZip("[ZIP]");

        card.setNumber("4242424242424242");
        card.setExpMonth(12);
        card.setExpYear(19);
        card.setCVC("123");

       stripe.createToken(card, "[YOUR_PK_TEST_KEY_HERE]", new TokenCallback() {
            public void onSuccess(MediaSession.Token token) {
                // TODO: Send Token information to your backend to initiate a charge
                Toast.makeText(getApplicationContext(), "Token created: " + token.getId(), Toast.LENGTH_LONG).show();
                tok = token;
                new StripeCharge(token.getId()).execute();
            }

            public void onError(Exception error) {
                Log.d("Stripe", error.getLocalizedMessage());
            }
        });
    }
    public class StripeCharge extends AsyncTask<String, Void, String> {
        String token;

        public StripeCharge(String token) {
            this.token = token;
        }

        @Override
        protected String doInBackground(String... params) {
            new Thread() {
                @Override
                public void run() {
                    postData(name,token,""+amount);
                }
            }.start();
            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Result",s);
        }
    }
    public void postData(String description, String token,String amount) {
        // Create a new HttpClient and Post Header
        try {
            URL url = new URL("[YOUR_SERVER_CHARGE_SCRIPT_URL]");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new NameValuePair("method", "charge"));
            params.add(new NameValuePair("description", description));
            params.add(new NameValuePair("source", token));
            params.add(new NameValuePair("amount", amount));

            OutputStream os = null;

            os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   */
}
