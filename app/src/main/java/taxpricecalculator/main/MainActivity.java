package taxpricecalculator.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import taxpricecalculator.main.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText priceInput;
    private CheckBox isTaxable;
    private ImageView addButton;
    private TextView totalPrice;
    private ListView pricelist;
    private static ListViewAdapter adapter;
    private static ArrayList<String> itemList;
    private double total;
    private static final double TAX = 1.13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setValues();

        if(addButton!= null){
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calcPrice();
                }
            });
        }

    }

    private void setValues(){
        priceInput = findViewById(R.id.priceInput);
        isTaxable = findViewById(R.id.isTaxable);
        addButton = findViewById(R.id.addButton);
        totalPrice = findViewById(R.id.totalPrice);
        pricelist = findViewById(R.id.pricelist);

        itemList = new ArrayList<>();
        adapter = new ListViewAdapter(getApplicationContext(), itemList);
        pricelist.setAdapter(adapter);
    }

    public static void addItemsToList(String price){
        itemList.add(price);
        adapter.notifyDataSetChanged();
    }
    public static void removeItemsFromList(int index){
        itemList.remove(index);
        adapter.notifyDataSetChanged();

    }

    private void calcPrice(){
        double price = Double.parseDouble(priceInput.getText().toString());
        if(isTaxable.isChecked()){
            price *= TAX;
        }

        total += price;
        DecimalFormat format = new DecimalFormat("#.##");
        String formattedTotal = format.format(total);

        addItemsToList(formattedTotal);

        totalPrice.setText("Total Price: $" + formattedTotal);


    }


}