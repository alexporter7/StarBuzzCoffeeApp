package alexporter7.github.starbuzzcoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

    StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId) {

        //======= Create Values for Drinks =======
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);

        //======= Insert the DB Row =======
        db.insert("DRINK", null, drinkValues);

    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            //======= Create the Drink Table =======
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT," +
                    "DESCRIPTION TEXT," +
                    "IMAGE_RESOURCE_ID INTEGER);");

            //======= Insert Drinks =======
            insertDrink(db, "Latte", "A couple of espresso shots with steamed milk", R.drawable.coffee1);
            insertDrink(db, "Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.coffee2);
            insertDrink(db, "Filter", "Highest quality beans roasted and brewed fresh", R.drawable.coffee3);
            insertDrink(db, "Cold Brewed", "Highest quality and richest cold brew over 8 hours", R.drawable.coffee4);

        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }
}
