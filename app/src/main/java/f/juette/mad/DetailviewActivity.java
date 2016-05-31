package f.juette.mad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import f.juette.mad.model.DataItem;

public class DetailviewActivity extends AppCompatActivity {

    private Button saveItemButton;
    private long calldelay;
    private EditText itemNameText;
    private MenuItem saveItemMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);

        itemNameText = (EditText) findViewById(R.id.item_name_text);
        saveItemButton = (Button) findViewById(R.id.callback_button);

        itemNameText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.i("DetailViewActivity", "ENTER!");
                handleItemNameInputCompleted();
                return true;
            }
            saveItemButton.setEnabled(false);
            saveItemMenuButton.setEnabled(false);
            return false;
        });

        // saveItemButton.setOnClickListener(v -> callbackCaller());

        // Get the data from the OverviewActivity
        long calltime = getIntent().getLongExtra("calltime", -1);
        Log.i("DetailviewActivity", "calltime: " + calltime);

        DataItem itemToEdit = (DataItem) getIntent().getSerializableExtra("editDataItem");
        if (itemToEdit != null) {
            itemNameText.setText(itemToEdit.getName());
        }

        calldelay = System.currentTimeMillis() - calltime;
    }

    private void handleItemNameInputCompleted() {
        String itemName = itemNameText.getText().toString();
        if (itemName.equals("lorem")) {
            String errormsg = getResources().getString(R.string.detailview_itemname_errormsg, itemName);
            itemNameText.setError(errormsg);
        }
        else {
            saveItemButton.setEnabled(true);
            saveItemMenuButton.setEnabled(true);
        }
    }

    private void callbackCaller() {
        // Log.i("DetailviewActivity", "callback: " + calldelay);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("calldelay", calldelay);
        returnIntent.putExtra("itemname", itemNameText.getText().toString());

        // Data back to the Overview activity
        setResult(Activity.RESULT_OK, returnIntent);

        // closing the activity
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailview_menu, menu);
        saveItemMenuButton = menu.findItem(R.id.saveItemAction);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveItemAction:
                callbackCaller();
                break;
        }
        return true;
    }
}
