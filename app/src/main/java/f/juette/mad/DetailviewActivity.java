package f.juette.mad;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DetailviewActivity extends AppCompatActivity {

    private Button callbackButton;
    private long calldelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);

        callbackButton = (Button) findViewById(R.id.callback_button);
        callbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackCaller();
            }
        });

        // Get the data from the OverviewActivity
        long calltime = getIntent().getLongExtra("calltime", -1);
        Log.i("DetailviewActivity", "calltime: " + calltime);

        calldelay = System.currentTimeMillis() - calltime;
    }

    private void callbackCaller() {
        // Log.i("DetailviewActivity", "callback: " + calldelay);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("calldelay", calldelay);

        // Data back to the Overview activity
        setResult(Activity.RESULT_OK, returnIntent);

        // closing the activity
        finish();
    }
}
