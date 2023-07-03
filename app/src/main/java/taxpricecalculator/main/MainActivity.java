package taxpricecalculator.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
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
        inputPrice = binding.priceInput;
        isTaxable = binding.isTaxable;
        priceListView = binding.priceListView;
        totalPrice = binding.totalPrice;

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
//                myAdapter.notifyItemInserted(listItems.size()-1);
                inputPrice.setText("");
            }
        });


        priceListView.setAdapter(myAdapter = new RecyclerView.Adapter<RowHolder>() {

            @NonNull
            @Override
            public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ListrowBinding bindingList = ListrowBinding.inflate(getLayoutInflater());
                return new RowHolder(bindingList.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull RowHolder holder, int position) {
                StoreItem obj = listItems.get(position);
                holder.itemPrice.setText(obj.toString());
                holder.rowNum.setText(position+1 +": ");
                holder.itemView.findViewById(R.id.remove).setOnClickListener(view->{
                    removeItem(position);
                });
            }

            @Override
            public int getItemCount() {
                return listItems.size();
            }
        });

        binding.priceListView.setLayoutManager(new LinearLayoutManager(this));
    }

    public class RowHolder extends RecyclerView.ViewHolder{
        TextView itemPrice;
        TextView rowNum;
        public RowHolder(@NonNull View itemView){
            super(itemView);
            itemPrice = itemView.findViewById(R.id.price);
            rowNum = itemView.findViewById(R.id.number);

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

    Toast t;
    private void makeToast(String str){
        if(t != null)t.cancel();
         t = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
         t.show();
    }

}