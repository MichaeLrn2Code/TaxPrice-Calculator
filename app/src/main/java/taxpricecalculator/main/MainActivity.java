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
    private ArrayAdapter<StoreItem> adapter;
    private ArrayList<StoreItem> itemList;
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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        pricelist.setAdapter(adapter);
    }

    private void addItemsToList(double price){
        itemList.add(new StoreItem(price));
        adapter.notifyDataSetChanged();
    }
    private void removeItemsFromList(){

    }

    private void calcPrice(){
        double price = Double.parseDouble(priceInput.getText().toString());
        if(isTaxable.isChecked()){
            price *= TAX;
        }

        addItemsToList(price);

        total += price;
        DecimalFormat format = new DecimalFormat("#.##");
        String formattedTotal = format.format(total);
        totalPrice.setText("Total Price: $" + formattedTotal);


    }


}