package f.juette.mad.model.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import f.juette.mad.model.DataItem;
import f.juette.mad.model.IDataItemCRUDOperations;

public class SQLiteDataItemCRUDOperationsImpl implements IDataItemCRUDOperations {

    // Logging object
    protected static final String logger = SQLiteDataItemCRUDOperationsImpl.class
            .getName();

    // Database name
    public static final String DBNAME = "dataItems.db";

    // Database version
    public static final int INITIAL_DBVERSION = 0;

    // Table name
    public static final String TABNAME = "dataitems";

    // Column names
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DELAY = "delay";

    // SQL-Create query
    public static final String TABLE_CREATION_QUERY = "CREATE TABLE " + TABNAME
            + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + COL_NAME + " TEXT,\n" + COL_DELAY + " INTEGER);";

    // General where clause for one item
    private static final String WHERE_IDENTIFY_ITEM = COL_ID + "=?";

    // Boolean as int values
    private static final int VALUE_EXPIRED = 1;

    // Database object
    private SQLiteDatabase db;

    public SQLiteDataItemCRUDOperationsImpl(Context context) {
        this.prepareSQLiteDatabase(context);
    }

    @Override
    public DataItem createDataItem(DataItem item) {
        Log.i(logger, "createDataItem(): " + item);

        ContentValues values = new ContentValues();
        values.put(COL_NAME, item.getName());
        values.put(COL_DELAY, item.getDelay());
        long id = db.insert(TABNAME, null, values);

        item.setId(id);

        return item;
    }

    @Override
    public List<DataItem> readAllDataItems() {
        Log.i(logger, "readAllDataItems()");

        List<DataItem> items = new ArrayList<DataItem>();

		/*
		 * declare the columns to be read out (id, name and expired) as a String
		 * array
		 */
        String[] columns = new String[] {COL_NAME, COL_DELAY, COL_ID};

		/* declare an ASC ordering for the id column */

		/* query the db taking a cursor as return value */
        Cursor cursor = db.query(TABNAME, columns, null, null, null, null, COL_ID + " ASC");

		/* use the cursor, moving to the first dataset */
        cursor.moveToFirst();

		/* iterate as long as we have reached the end */
        while (!cursor.isAfterLast()) {
			/* create an item from the current cursor position */
            DataItem item = new DataItem();
            item.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
            item.setDelay(cursor.getLong(cursor.getColumnIndex(COL_DELAY)));
            item.setId(cursor.getLong(cursor.getColumnIndex(COL_ID)));
            items.add(item);
            Log.i(logger, "created DataItem: " + item);

			/* move the cursor to the next item */
            cursor.moveToNext();
        }
        Log.i(logger, "created DataItems: " + items);

		/* return the items */
        return items;
    }

//	@Override
//	public DataItem readDataItem(long dataItemId) {
//		Log.i(logger, "readDataItem(): " + dataItemId);
//
//		/*
//		 * query the db obtaining a cursor as return value and passing the where
//		 * prepared statement and the id within a string array
//		 */
//
//		/* check how many items we have and move to first one */
//			/* create the item from the cursor if we have got one */
//
//		return null;
//	}

//	@Override
//	public DataItem updateDataItem(DataItem item) {
//		Log.i(logger, "updateDataItem(): " + item);
//
//		/* as in create, create the content values object from the item */
//
//		/*
//		 * then update the item in the db using the prepared statement for the
//		 * where clause and passing the id of the item as a string
//		 * we get the number of updated rows as a return value
//		 */
//
//		/* and return the item */
//		return item;
//	}
//
//	@Override
//	public boolean deleteDataItem(long dataItemId) {
//		Log.i(logger, "deleteDataItem(): " + dataItemId);
//
//		/*
//		 * delete the item passing the prepared where clause and the item id as
//		 * string, capture the return value indicating how many items have been
//		 * deleted
//		 */
//
//		/* check the return value from the deletion and return it */
//		return false;
//	}

	/*
	 * helper methods for ORM etc.
	 */

    /**
     * create a ContentValues object which can be passed to a db query
     *
     * @param item
     * @return
     */
    private ContentValues createContentValues(DataItem item) {

		/* create a content values object */

		/* put the name and the expired attributes from the item into the object */

		/* return the object */
        return null;
    }

    /**
     * create an item from the cursor
     *
     * @param c
     * @return
     */
    public DataItem createItemFromCursor(Cursor c) {

        return null;
    }

    /**
     * prepare the database
     */
    protected void prepareSQLiteDatabase(Context context) {

		/* create the database or leave it as it is */
        this.db = context.openOrCreateDatabase(DBNAME,
                SQLiteDatabase.CREATE_IF_NECESSARY, null);

		/* we need to check which version we have... */
        Log.d(logger, "db version is: " + db.getVersion());
        if (this.db.getVersion() == INITIAL_DBVERSION) {
            Log.i(logger,
                    "the db has just been created. Need to create the table...");
            db.setLocale(Locale.getDefault());
			/* update the version */
            db.setVersion(INITIAL_DBVERSION + 1);
			/* and excute the table creation */
            db.execSQL(TABLE_CREATION_QUERY);
        } else {
            Log.i(logger, "the db exists already. No need for table creation.");
        }

    }

    public void finalise() {
		/* close the db to avoid trouble */
        this.db.close();
        Log.i(logger, "db has been closed");
    }
}
