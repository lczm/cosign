package sg.edu.np.cosign.ui.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import sg.edu.np.cosign.MainActivity;
import sg.edu.np.cosign.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    DatePickerDialog picker;
    EditText eText, goal_edit;
    Button btnGet;
    TextView tvw;
    Switch aSwitch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        /*final TextView textView = root.findViewById(R.id.hellotxt);
        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        final EditText eText= root.findViewById(R.id.Date_editor);
        final EditText goal_edit= root.findViewById(R.id.goal_et);
        goal_edit.setFilters(new InputFilter[]{ new MaxMinClass("1", "36")});
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        btnGet = root.findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvw = getView().findViewById(R.id.textView1);
                tvw.setText("Selected Date: "+ eText.getText() + ", Goal: " + goal_edit.getText());
            }
        });
        aSwitch = root.findViewById(R.id.Noti_switch);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch;
                if (aSwitch.isChecked())
                    statusSwitch = aSwitch.getTextOn().toString();
                else
                    statusSwitch = aSwitch.getTextOff().toString();
                Toast.makeText(getActivity().getApplicationContext(), "Notification :" + statusSwitch, Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}