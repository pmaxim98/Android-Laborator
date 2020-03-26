package lab1.tppa.laboratortest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.lab1.tppa.MESSAGE";

    public static final String PRODUCTS_FILENAME = "Products";

    private int productIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","Function: onCreate");

        Log.d("MainActivity", "Clearing from storage");
        clearAllProductsFromStorage();

        Log.d("MainActivity", "Saving test products to storage");
        saveProductsToStorage(createTestProducts());

        Log.d("MainActivity", "Getting products from storage");
        ArrayList<Product> products = loadProductsFromStorage();

        Log.d("MainActivity", "Total products taken from storage");
        Log.d("MainActivity", String.valueOf(products.size()));

        Log.d("MainActivity", "Adding products to listview");
        ListView listView = (ListView) findViewById(R.id.listView);

        final CustomListAdapter adapter = new CustomListAdapter(this, products);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                productIndex = position;
                Product product = (Product) adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "Description: " + product.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAllProductsFromStorage()
    {
        File temp = new File(PRODUCTS_FILENAME);

        try {
            if (temp.exists()) {
                RandomAccessFile raf = new RandomAccessFile(temp, "rw");
                raf.setLength(0);
            }
        }
        catch (Exception e) { }
    }

    private void saveProductsToStorage(ArrayList<Product> products)
    {
        try (FileOutputStream fos = this.getApplicationContext().openFileOutput(PRODUCTS_FILENAME, Context.MODE_PRIVATE);
             ObjectOutputStream os = new ObjectOutputStream(fos))
        {
            os.writeObject(products);
        }
        catch (Exception e) {
            Log.d("MainActivity", e.getMessage());
        }
    }

    private ArrayList<Product> loadProductsFromStorage()
    {
        ArrayList<Product> products = null;

        try (FileInputStream fis = this.getApplicationContext().openFileInput(PRODUCTS_FILENAME);
             ObjectInputStream is = new ObjectInputStream(fis))
        {
            products = (ArrayList<Product>) is.readObject();
        }
        catch (Exception e) {
            Log.d("MainActivity", e.getMessage());
        }

        return products;
    }

    private ArrayList<Product> createTestProducts()
    {
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("TestName1", 10, "TestDesc1"));
        products.add(new Product("TestName2", 15, "TestDesc2"));
        products.add(new Product("TestName3", 7, "TestDesc3"));
        products.add(new Product("TestName4", 10, "TestDesc4"));
        products.add(new Product("TestName5", 15, "TestDesc5"));
        products.add(new Product("TestName6", 7, "TestDesc6"));
        products.add(new Product("TestName7", 10, "TestDesc7"));
        products.add(new Product("TestName8", 15, "TestDesc8"));
        products.add(new Product("TestName9", 7, "TestDesc9"));
        products.add(new Product("TestName10", 7, "TestDesc10"));
        products.add(new Product("TestName11", 10, "TestDesc11"));
        products.add(new Product("TestName12", 15, "TestDesc12"));
        products.add(new Product("TestName13", 7, "TestDesc13"));

        return products;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","Function: onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","Function: onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","Function: onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","Function: onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","Function: onStart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_tab_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.settings:
                startSettings();
                break;
            case R.id.sensors:
                startSensors();
                break;
            case R.id.test1:
                startNewActivity();
                break;
            case R.id.test2:
                showAlertDialog();
                break;
            case R.id.test3:
                Toast.makeText(getApplicationContext(), "Toast!", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void startSensors() {
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }

    public void startNewActivity() {
        Intent intent = new Intent(this, SecondActivity.class);

        ListView listView = (ListView) findViewById(R.id.listView);
        ListAdapter adapter = listView.getAdapter();
        Product product = (Product) adapter.getItem(productIndex);

        intent.putExtra(EXTRA_MESSAGE, product.getDescription());
        startActivity(intent);
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("A Nice Title...");
        builder.setMessage("Hello!");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You clicked YES", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.setNegativeButton(
                "Nooo!!!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You clicked NO", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        Log.d("MainActivity","Function: onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        Log.d("MainActivity","Function: onRestoreInstanceState");
    }
}
