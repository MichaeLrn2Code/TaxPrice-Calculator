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

import java.util.ArrayList;

import taxpricecalculator.main.databinding.ActivityMainBinding;
import taxpricecalculator.main.databinding.ListrowBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ListrowBinding rowBinding;

    ArrayList<StoreItem> listItems = new ArrayList<>();

    ListItemViewModel listModel;

    private RecyclerView.Adapter myAdapter;

    EditText inputPrice;

    CheckBox isTaxable;

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

        binding.addButton.setOnClickListener(clk->{
            String price = inputPrice.getText().toString();
            if (price == null || price.length() == 0){
                makeToast("Enter a price!");
            }else{
                if (isTaxable.isChecked()){
                double taxedPrice = Double.parseDouble(price)*TAX;
                    listItems.add(new StoreItem(taxedPrice));
                }else {
                listItems.add(new StoreItem(Double.parseDouble(price)));}
                myAdapter.notifyItemInserted(listItems.size()-1);
                inputPrice.setText("");
            }
        });

        binding.priceListView.setAdapter(myAdapter = new RecyclerView.Adapter<RowHolder>() {

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

//                holder.itemView.findViewById(R.id.remove).setOnClickListener(view->{
//                    listItems.remove(position);
//                    myAdapter.notifyItemRemoved(position);
//                });
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
        public RowHolder(@NonNull View itemView){
            super(itemView);
            itemPrice = itemView.findViewById(R.id.price);

        }
    }
    Toast t;
    private void makeToast(String str){
        if(t != null)t.cancel();
         t = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
         t.show();
    }

}