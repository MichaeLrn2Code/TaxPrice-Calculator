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
    private static TextView totalPrice;
    private static ListView pricelist;
    private static ListViewAdapter adapter;
    private static ArrayList<String> itemList;
    private static double total;
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
        adapter = new ListViewAdapter(this, itemList);
        pricelist.setAdapter(adapter);
    }

    public static void addItemsToList(String price){

        double priceDouble = Double.parseDouble(price);
        total += priceDouble;
        totalPrice.setText("$" + formatTOPrice(total));

        itemList.add(price);
        pricelist.setAdapter(adapter);

    }
    public static void removeItemsFromList(int index){
        total -= Double.parseDouble(itemList.get(index));
        totalPrice.setText("$" + formatTOPrice(total));

        itemList.remove(index);
        pricelist.setAdapter(adapter);

    }
    private static String formatTOPrice(Double value){
        DecimalFormat format = new DecimalFormat("#.##");
       return format.format(value);
    }
    public void calcPrice(){
        String input = priceInput.getText().toString();
        if(input == null || input.length() == 0)
            return;
        double price = Double.parseDouble(input);
        priceInput.setText("");

        if(isTaxable.isChecked()){
            price *= TAX;
        }

        addItemsToList(formatTOPrice(price));




    }


}