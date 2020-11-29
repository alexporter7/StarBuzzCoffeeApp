package alexporter7.github.starbuzzcoffee;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkID";

    public static final int NAME_COLUMN = 0;
    public static final int DESC_COLUMN = 1;
    public static final int IMAGE_COLUMN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //=== Get the Drink by ID ===
        int drinkID = (int) getIntent().getExtras().get(EXTRA_DRINKID);

        //=== Get Drinks from Database
        SQLiteOpenHelper starbuzzDataHelper = new StarbuzzDatabaseHelper(this);
        try {
            //=== Init the fields depending on chosen drink ===
            TextView name = (TextView) findViewById(R.id.name);
            TextView desc = (TextView) findViewById(R.id.description);
            ImageView photo = (ImageView) findViewById(R.id.photo);
            CheckBox favorite = (CheckBox) findViewById(R.id.favorite);

            SQLiteDatabase db = starbuzzDataHelper.getReadableDatabase();
            Cursor cur = db.query(
                    "DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkID)},
                    null, null, null
            );

            if (cur.moveToFirst()) {
                name.setText(cur.getString(NAME_COLUMN));
                desc.setText(cur.getString(DESC_COLUMN));
                photo.setImageResource(cur.getInt(IMAGE_COLUMN));
                photo.setContentDescription(cur.getString(NAME_COLUMN));
                boolean isFavorite = (cur.getInt(3) == 1);
                favorite.setChecked(isFavorite);
            }

            cur.close();
            db.close();
        }
        catch (SQLException e) { Toast.makeText(this, "Database Unavailable", Toast.LENGTH_LONG).show(); }
        catch (Exception e) { e.printStackTrace(); }

    }

    public void onFavoriteClicked(View view) {

        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);
        CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVORITE", favorite.isChecked());

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);

        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update(
                    "DRINK",
                    drinkValues,
                    "_id = ?",
                    new String[] { Integer.toString(drinkId) }
            );
            db.close();
        }
        catch (SQLException e) { Toast.makeText(this, "Database Unavailable", Toast.LENGTH_LONG).show(); }
        catch (Exception e) { e.printStackTrace(); }

    }

}