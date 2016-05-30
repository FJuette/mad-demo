package f.juette.mad;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailviewActivity extends AppCompatActivity {

    private Button callbackButton;
    private long calldelay;
    private EditText itemNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);

        itemNameText = (EditText) findViewById(R.id.item_name_text);
        callbackButton = (Button) findViewById(R.id.callback_button);

        itemNameText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.i("DetailViewActivity", "ENTER!");
                handleItemNameInputCompleted();
                return true;
            }
            callbackButton.setEnabled(false);
            return false;
        });

        callbackButton.setOnClickListener(v -> callbackCaller());

        // Get the data from the OverviewActivity
        long calltime = getIntent().getLongExtra("calltime", -1);
        Log.i("DetailviewActivity", "calltime: " + calltime);

        calldelay = System.currentTimeMillis() - calltime;
    }

    private void handleItemNameInputCompleted() {
        String itemName = itemNameText.getText().toString();
        if (itemName.equals("lorem")) {
            String errormsg = getResources().getString(R.string.detailview_itemname_errormsg, itemName);
            itemNameText.setError(errormsg);
        }
        else {
            callbackButton.setEnabled(true);
        }
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
