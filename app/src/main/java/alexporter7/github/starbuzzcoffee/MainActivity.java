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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //=== Click Listeners===
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
//                else if (position == 1) {
//                    Intent intent = new Intent(MainActivity.this, FoodCategoryActivity.class);
//                    startActivity(intent);
//                }
//                else if (position == 2) {
//                    Intent intent = new Intent(MainActivity.this, StoreCategoryActivity.class);
//                    startActivity(intent);
//                }
            }
        };

        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

    }
}