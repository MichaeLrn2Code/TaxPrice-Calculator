package taxpricecalculator.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import taxpricecalculator.main.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    ArrayList<StoreItem> listItems = new ArrayList<>();

    ListItemViewModel listModel;

    private RecyclerView.Adapter myAdapter;

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

        binding.priceListView.setAdapter(myAdapter = new RecyclerView.Adapter<RowHolder>() {

            @NonNull
            @Override
            public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RowHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
    }

    public class RowHolder extends RecyclerView.ViewHolder{
        public RowHolder(@NonNull View itemView){
            super(itemView);

        }
    }
}