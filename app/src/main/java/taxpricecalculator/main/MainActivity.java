package taxpricecalculator.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import taxpricecalculator.main.databinding.ActivityMainBinding;
import taxpricecalculator.main.databinding.ListrowBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    ArrayList<StoreItem> listItems = new ArrayList<>();

    ListItemViewModel listModel;

    private RecyclerView.Adapter myAdapter;

    EditText inputPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listModel = new ViewModelProvider(this).get(ListItemViewModel.class);
        listItems = listModel.items.getValue();

        if (listItems == null){
            listModel.items.postValue(listItems = new ArrayList<StoreItem>());
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        inputPrice = binding.priceInput;

        binding.addButton.setOnClickListener(clk->{
            listItems.add(new StoreItem(Double.parseDouble(inputPrice.getText().toString())));
            myAdapter.notifyItemInserted(listItems.size()-1);
            inputPrice.setText("");
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
}