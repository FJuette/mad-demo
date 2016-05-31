package f.juette.mad;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import f.juette.mad.model.impl.DataItem;
import f.juette.mad.model.IDataItemCRUDOperations;
import f.juette.mad.model.impl.SQLiteDataItemCRUDOperationsImpl;

public class OverviewActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button callDetailviewButton;
    private ViewGroup listview;
    private Button selectContactButton;
    private ProgressDialog progressDialog;

    private ArrayAdapter<DataItem> adapter;

    private IDataItemCRUDOperations crudops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Read the view elements
        welcomeText = (TextView) findViewById(R.id.welcome_text);
        callDetailviewButton = (Button) findViewById(R.id.call_detailview_button);
        listview = (ViewGroup) findViewById(R.id.listview);
        selectContactButton = (Button) findViewById(R.id.select_contact_button);
        progressDialog = new ProgressDialog(this);

        adapter = new ArrayAdapter<DataItem>(this, R.layout.overview_listitem_simple, new ArrayList<DataItem>());
        ((ListView)listview).setAdapter(adapter);

        // Actions with the elements
        callDetailviewButton.setOnClickListener(v -> createNewItem());
        selectContactButton.setOnClickListener(v -> selectContact());

        ((ListView)listview).setOnItemClickListener((parent, view, position, id) -> {
            DataItem selectedItem = adapter.getItem(position);
            // Toast.makeText(OverviewActivity.this, "selectedItem: " + selectedItem.getName(), Toast.LENGTH_SHORT).show();
            showItemDetails(selectedItem);
        });

        registerForContextMenu(listview);

        crudops = new SQLiteDataItemCRUDOperationsImpl(this);

        // Set values/content on elements
        welcomeText.setText(R.string.welcome2);

        readItemsAndFillListView();
    }

    private void readItemsAndFillListView() {

        new AsyncTask<Void, Void, List<DataItem>>() {

            @Override
            protected void onPreExecute() {
                progressDialog.show();
            }

            @Override
            protected List<DataItem> doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return crudops.readAllDataItems();
            }

            @Override
            protected void onPostExecute(List<DataItem> dataItems) {
                for (DataItem item : dataItems) {
                    addDataItemToListView(item);
                }
                progressDialog.hide();
            }
        }.execute();

    }

    private void createNewItem() {
        // Toast.makeText(OverviewActivity.this, "OnClick on Call!", Toast.LENGTH_SHORT).show();
        long calltime = System.currentTimeMillis();

        // Set the activity to call
        Intent callDetailviewIntent = new Intent(this, DetailviewActivity.class);

        // Set the data for the DetailviewActivity
        callDetailviewIntent.putExtra("calltime", calltime);

        // Open the DetailviewActivity
        startActivityForResult(callDetailviewIntent, 0);
    }

    private void showItemDetails(DataItem item) {

        Intent callDetailviewIntent = new Intent(this, DetailviewActivity.class);
        callDetailviewIntent.putExtra("editDataItem", item);

        startActivityForResult(callDetailviewIntent, 1);
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
            String itemname = data.getStringExtra("itemname");

            DataItem newItem = new DataItem(itemname, calldelay);
            createAndShowNewDataItem(newItem);

        }
        else if (requestCode == 1) {
            Log.i("OverviewActivity", "contact pick intent: " + data);
        }

    }

    private void createAndShowNewDataItem(final DataItem item) {
        Log.i("OverviewActivity", "dataitem: " + item);

        // Runnable in thread with lambda
        /*new Thread(() -> {
            final DataItem createdItem = createDataItem(item);
            runOnUiThread(() -> {
                addDataItemToListView(createdItem);
            });
        }).start();*/

        // Same function as the thread but more readable
        // 1. type of parameter, 2. datatype during run, 3. return type
        new AsyncTask<DataItem, Void, DataItem>() {

            @Override
            protected void onPreExecute() {
                progressDialog.show();
            }

            @Override
            protected DataItem doInBackground(DataItem... params) {
                return createDataItem(params[0]);
            }

            @Override
            protected void onPostExecute(DataItem dataItem) {
                addDataItemToListView(dataItem);
                progressDialog.hide();
            }
        }.execute(item);
    }

    private DataItem createDataItem(DataItem item) {
        item = crudops.createDataItem(item);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("OverviewActivity", item.toString());
        return item;
    }

    private void addDataItemToListView(DataItem item) {
        // ViewGroup listitemView = (ViewGroup) getLayoutInflater().inflate(R.layout.overview_listitem, listview, false);
        // TextView itemNameText = (TextView) listitemView.findViewById(R.id.item_name);
        // itemNameText.setText(item.getDelay() + " -- " + item.getName());

        // listview.addView(listitemView);
        // listview.setText(listview.getText().toString() + "\n" + calldelay + " -- " + itemname);
        adapter.add(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overview_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.overview_listview_contextmenu, menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.newItemAction) {
            createNewItem();
        }
        else if (item.getItemId() == R.id.addContactAction) {
            selectContact();
        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        DataItem selectedItem = adapter.getItem(((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position);

        if (item.getItemId() == R.id.deleteItemAction) {
            Toast.makeText(OverviewActivity.this, "delete: " + selectedItem.getName(), Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.editItemAction) {
            // Toast.makeText(OverviewActivity.this, "edit: " + selectedItem.getName(), Toast.LENGTH_SHORT).show();
            showItemDetails(selectedItem);
        }
        return true;
    }
}