package f.juette.mad;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OverviewActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button callDetailviewButton;
    private TextView listview;
    private Button selectContactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Read the view elements
        welcomeText = (TextView) findViewById(R.id.welcome_text);
        callDetailviewButton = (Button) findViewById(R.id.call_detailview_button);
        listview = (TextView) findViewById(R.id.listview);
        selectContactButton = (Button) findViewById(R.id.select_contact_button);

        // Actions with the elements
        callDetailviewButton.setOnClickListener(v -> callDetailviewActivity());
        selectContactButton.setOnClickListener(v -> selectContact());

        // Set values/content on elements
        welcomeText.setText(R.string.welcome2);
    }

    private void callDetailviewActivity() {
        // Toast.makeText(OverviewActivity.this, "OnClick on Call!", Toast.LENGTH_SHORT).show();
        long calltime = System.currentTimeMillis();

        // Set the activity to call
        Intent callDetailviewIntent = new Intent(this, DetailviewActivity.class);

        // Set the data for the DetailviewActivity
        callDetailviewIntent.putExtra("calltime", calltime);

        // Open the DetailviewActivity
        startActivityForResult(callDetailviewIntent, 0);
    }

    private void selectContact() {
        Intent selectContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(selectContactIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            // Return value from the DetailviewActivity
            long calldelay = data.getLongExtra("calldelay", -1);
            Log.i("OverviewActivity", "calldelay: " + calldelay);

            listview.setText(listview.getText().toString() + "\n" + calldelay);
        }
        else if (requestCode == 1) {
            Log.i("OverviewActivity", "contact pick intent: " + data);
        }

    }
}