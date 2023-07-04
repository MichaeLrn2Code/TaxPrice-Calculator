package taxpricecalculator.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import taxpricecalculator.main.databinding.ActivityMainBinding;
import taxpricecalculator.main.databinding.ListrowBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ListrowBinding rowBinding;

    ArrayList<StoreItem> listItems = new ArrayList<>();

    ListItemViewModel listModel;

    RecyclerView priceListView;

    private double total;

    private RecyclerView.Adapter myAdapter;

    EditText inputPrice;

    CheckBox isTaxable;

    TextView totalPrice;

    ImageView clearList;



    private static final double TAX = 1.13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listModel = new ViewModelProvider(this).get(ListItemViewModel.class);
        listItems = listModel.items.getValue();

        if (listItems == null){
            listModel.items.postValue(listItems = new ArrayList<StoreItem>());
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        rowBinding = ListrowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the value
        inputPrice = binding.priceInput;
        isTaxable = binding.isTaxable;
        priceListView = binding.priceListView;
        totalPrice = binding.totalPrice;
        clearList = binding.clearList;

        // On Click Listener to add a new list item
        binding.addButton.setOnClickListener(clk->{
            String price = inputPrice.getText().toString();
            if (price == null || price.length() == 0){
                makeToast("Enter a price!");
            }else{
                if (isTaxable.isChecked()){
                double taxedPrice = Double.parseDouble(price)*TAX;
                    addItem(new StoreItem(taxedPrice));
                }else {
                addItem(new StoreItem(Double.parseDouble(price)));}
                inputPrice.setText("");
            }
        });


        // On Click Listener to clear the list
        clearList.setOnClickListener(clk->{
            clearList();
        });

        // Method to render the list item
        priceListView.setAdapter(myAdapter = new RecyclerView.Adapter<RowHolder>() {

            @NonNull
            @Override
            public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ListrowBinding bindingList = ListrowBinding.inflate(getLayoutInflater(),parent,false);
                return new RowHolder(bindingList.getRoot());
            }

            // Method to set the value of a showing row item
            @Override
            public void onBindViewHolder(@NonNull RowHolder holder, int position) {
                StoreItem obj = listItems.get(position);
                holder.itemPrice.setText(obj.toString());
                holder.rowNum.setText(position+1 +": ");

                // On Click listener for copy icon to duplicate list item
//                holder.itemView.findViewById(R.id.copy).setOnClickListener(view->{
//                    addItem(listItems.get(position));
//                });


                // On click Listener for remove icon to remove the list item when user click the remove button on specific row
//                holder.itemView.findViewById(R.id.remove).setOnClickListener(view->{
//                    removeItem(position);
//                });
            }

            @Override
            public int getItemCount() {
                return listItems.size();
            }
        });

        // Use layout Manager to set the one-dimension column layout
        binding.priceListView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Class to encapsulates the information of a row item
    public class RowHolder extends RecyclerView.ViewHolder{
        TextView itemPrice;
        TextView rowNum;
        public RowHolder(@NonNull View itemView){
            super(itemView);
            itemPrice = itemView.findViewById(R.id.price);
            rowNum = itemView.findViewById(R.id.number);

            // On Click listener for copy icon to duplicate list item
            itemView.findViewById(R.id.copy).setOnClickListener(click->{
                int rowToCopy = getAbsoluteAdapterPosition();
                addItem(listItems.get(rowToCopy));
            });

            // On click Listener for remove icon to remove the list item when user click the remove button on specific row
            itemView.findViewById(R.id.remove).setOnClickListener(click->{
                int rowToDelete = getAbsoluteAdapterPosition();
                removeItem(rowToDelete);
            });

        }
    }

    public void addItem(StoreItem item){
        listItems.add(item);
        double priceDouble = item.getPrice();
        total += priceDouble;
        totalPrice.setText("$" + formatTOPrice(total));
        myAdapter.notifyItemInserted(listItems.size()-1);
    }

    private String formatTOPrice(Double value){
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(value);
    }

    public void removeItem(int index){
        total -= listItems.get(index).getPrice();
        totalPrice.setText("$" + formatTOPrice(total));
        listItems.remove(index);
        myAdapter.notifyDataSetChanged();


    }

    public void clearList(){
        totalPrice.setText("$" + 0);
        listItems.clear();
        myAdapter.notifyDataSetChanged();
    }

    Toast t;
    private void makeToast(String str){
        if(t != null)t.cancel();
         t = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
         t.show();
    }

}