package com.heena.emailvalidation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
   /* EditText editTextEmail;
    String strEmailAddress, email;
    Button checkButton;
    boolean flag;
*/
   Button emailButton;
    EditText emailEdittext;
    TextView textviewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailButton = (Button) findViewById(R.id.sendButton);
        emailEdittext = (EditText) findViewById(R.id.emailEditText);
        textviewMessage = (TextView) findViewById(R.id.textViewmessage);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = emailEdittext.getText().toString();
                String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                if (getText.matches(Expn) && getText.length() > 0) {
                    textviewMessage.setText("valid email");
                } else {
                    textviewMessage.setText("invalid email");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }




   /* public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +""
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        checkButton = (Button) findViewById(R.id.btnValidateEmail);

        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        checkButton=(Button) findViewById(R.id.btnValidateEmail);

        checkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email=editTextEmail.getText().toString();
                if(checkEmail(email))
                    Toast.makeText(MainActivity.this,"Valid Email Addresss", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this,"Invalid Email Addresss", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }*/

}
