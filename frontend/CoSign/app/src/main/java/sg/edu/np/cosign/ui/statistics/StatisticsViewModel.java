package sg.edu.np.cosign.ui.statistics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

public class StatisticsViewModel extends ViewModel {
private MutableLiveData<String> mText;

public StatisticsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is statistics fragment");
        }

public LiveData<String> getText() {
        return mText;
        }
        }