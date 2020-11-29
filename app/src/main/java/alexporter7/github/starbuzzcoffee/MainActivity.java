package alexporter7.github.starbuzzcoffee;

//Categories
//Drinks
//  - Drinks that are sold
//      - Product Page (product, details
//Food
//  - Food that is sold
//      - Product Page
//Stores?

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor favoriteCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeOptionsListView();
        setupFavoritesListView();

    }

    private void initializeOptionsListView() {
        //=== Click Listeners===
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    private void setupFavoritesListView() {
        ListView listFavorites = (ListView) findViewById(R.id.favorite_drinks);
        SQLiteOpenHelper starbuzzDataHelper = new StarbuzzDatabaseHelper(this);
        try {
            db = starbuzzDataHelper.getReadableDatabase();
            favoriteCursor = db.query(
                    "DRINK",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null
            );
            SimpleCursorAdapter favoriteAdapter = new SimpleCursorAdapter(
                    MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoriteCursor,
                    new String[] { "NAME" },
                    new int[] { android.R.id.text1 }, 0
            );
            listFavorites.setAdapter(favoriteAdapter);
        }
        catch (SQLException e) { Toast.makeText(this, "Database Unavailable", Toast.LENGTH_LONG).show(); }
        catch (Exception e) { e.printStackTrace(); }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteCursor.close();
        db.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Cursor newCursor = db.query(
                "DRINK",
                new String[] {"_id", "NAME"},
                "FAVORITE = 1",
                null, null, null, null
        );
        ListView listFavorites = (ListView) findViewById(R.id.favorite_drinks);
        CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
        adapter.changeCursor(newCursor);
        favoriteCursor = newCursor;
    }
}