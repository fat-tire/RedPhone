package org.thoughtcrime.redphone.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.thoughtcrime.redphone.R;

public class RegistrationProblemsActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.registration_problems);
    setTitle(getString(R.string.RegistrationProblemsActivity_possible_problems));

    ((Button)findViewById(R.id.close_button)).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
}
