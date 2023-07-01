package taxpricecalculator.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ListItemViewModel extends ViewModel {
    public MutableLiveData<ArrayList<StoreItem>> items = new MutableLiveData<>();

}
