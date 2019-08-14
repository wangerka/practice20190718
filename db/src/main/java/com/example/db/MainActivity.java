package com.example.db;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView mListView;
    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.list);

        String[] dataColumns = {ContactsContract.Contacts.DISPLAY_NAME/*, ContactsContract.CommonDataKinds.Phone.NUMBER*/};
        int[] viewIDs = { android.R.id.text1/*,android.R.id.text2*/ };

        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                null, dataColumns, viewIDs, 0);

        mListView.setAdapter(mAdapter);

        LoaderManager lm = getLoaderManager();
        lm.initLoader(1, null, this);

    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor o) {
        mAdapter.swapCursor(o);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);

    }
}
