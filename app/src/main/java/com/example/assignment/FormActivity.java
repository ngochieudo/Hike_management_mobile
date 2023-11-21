package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.databinding.ActivityFormBinding;
import com.example.assignment.db.AppDatabase;
import com.example.assignment.db.Dao.HikeDao;
import com.example.assignment.db.DatabaseHelper;
import com.example.assignment.db.entity.Hike;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    //    private DatabaseHelper db;
    private AppDatabase db;
    private HikeDao dao;
    private ActivityFormBinding binding;
    private Hike hike;
    boolean isUpdated = false;
    private String parkingValue;
    private String difficultyValue;
    private String date;

    public static void showDatePickerDialog(Context context, EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            String date = DATE_FORMAT.replace("yyyy", String.valueOf(year))
                    .replace("MM", String.valueOf(month + 1))
                    .replace("dd", String.valueOf(dayOfMonth));
            editText.setText(date);
        }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void uncheckedUnselectedRadioButtons(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (radioButton.getId() != checkedId) {
                radioButton.setChecked(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormBinding.inflate(getLayoutInflater());
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "hike-db")
                .allowMainThreadQueries()
                .build();
        dao = db.hikeDao();
        binding.formToolbar.setTitle(isUpdated ? "Update Hike" : "Create Hike");
        // set back button
        setSupportActionBar(binding.formToolbar);
        binding.formToolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        hike = (Hike) getIntent().getSerializableExtra("hike");
        final TextInputEditText hikeName = binding.hikeName;
        final TextInputEditText hikeLocation = binding.hikeLocation;
//        final Button hikeDate = view.findViewById(R.id.edit_date);
        final TextInputEditText hikeLength = binding.hikeLength;
        final RadioGroup hikeParking = binding.hikeParking;
        final RadioGroup hikeDifficulty = binding.hikeDifficulty;
        final TextInputEditText hikeDesc = binding.hikeDesc;
        final TextInputEditText hikeGuide = binding.hikeGuide;
        hike = (Hike) getIntent().getSerializableExtra("hike");

        binding.hikeDate.setOnClickListener(view -> showDatePickerDialog(FormActivity.this, binding.hikeDate));

        hikeParking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // unchecked all radio buttons if one is checked
                uncheckedUnselectedRadioButtons(radioGroup, checkedId);
                parkingValue = ((RadioButton) findViewById(checkedId)).getText().toString();

            }
        });

        hikeDifficulty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                uncheckedUnselectedRadioButtons(radioGroup, checkedId);
                // convert value of radio button to string and save it to difficultyValue
                difficultyValue = ((RadioButton) findViewById(checkedId)).getText().toString();
            }
        });


        if (isUpdated && hike != null) {
            int difficultyId = getRadioButtonIdByValue(hikeDifficulty, hike.getDifficulty());
            int parkingId = getRadioButtonIdByValue(hikeParking, hike.getParking());

            hikeName.setText(hike.getName());
            hikeLocation.setText(hike.getLocation());
            hikeLength.setText(hike.getLength().toString());
            if (parkingId != -1) {
                hikeParking.check(parkingId);
            }
            if (difficultyId != -1) {
                hikeDifficulty.check(difficultyId);
            }
            hikeDesc.setText(hike.getDesc());
            hikeGuide.setText(hike.getGuide());
            binding.confirmBtn.setOnClickListener(view -> {
                if (validateHike(
                        hikeName.getText().toString(),
                        hikeLocation.getText().toString(),
                        hikeLength.getText().toString(),
                        parkingValue,
                        difficultyValue,
                        hikeDesc.getText().toString(),
                        hikeGuide.getText().toString()
                )) {
                    confirmUpdate(
                            hike.getId(),
                            hikeName.getText().toString(),
                            hikeLocation.getText().toString(),
                            date,
                            Double.parseDouble(hikeLength.getText().toString()),
                            hikeDesc.getText().toString(),
                            hikeGuide.getText().toString(),
                            parkingValue,
                            difficultyValue

                    );
                    finish();
                }
            });
        } else {
            binding.confirmBtn.setOnClickListener(view -> {
                if (!validateHike(
                        hikeName.getText().toString(),
                        hikeLocation.getText().toString(),
                        hikeLength.getText().toString(),
                        parkingValue,
                        difficultyValue,
                        hikeDesc.getText().toString(),
                        hikeGuide.getText().toString()
                )) {
                    return;
                }
                confirmCreate(
                        hikeName.getText().toString(),
                        hikeLocation.getText().toString(),
                        date,
                        Double.parseDouble(hikeLength.getText().toString()),
                        hikeDesc.getText().toString(),
                        hikeGuide.getText().toString(),
                        parkingValue,
                        difficultyValue
                );
                finish();
            });
        }


        setContentView(binding.getRoot());

    }

    // on clicking confirm button
    private void confirmCreate(String name,
                               String location, String date,
                               Double length,
                               String desc,
                               String guide,
                               String parking,
                               String difficulty
    ) {
        try {
            dao.insertHike(name, location, date, length, desc, guide, parking, difficulty);
            Toast.makeText(this, "Hike created successfully", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Hike creation failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    private void confirmUpdate(int id,
                               String name,
                               String location,
                               String date,
                               Double length,
                               String desc,
                               String guide,
                               String parking,
                               String difficulty

    ) {

//        hikeArrayList.set(position, hike);
        try {
            dao.updateHike(id,name, location, date, length, desc, guide, parking, difficulty);
            Toast.makeText(this, "Hike updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Hike updated failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
//        hikesAdapter.notifyDataSetChanged();
    }

    private int getRadioButtonIdByValue(RadioGroup radioGroup, String value) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View radioButton = radioGroup.getChildAt(i);
            if (radioButton instanceof RadioButton) {
                RadioButton currentRadioButton = (RadioButton) radioButton;
                if (currentRadioButton.getText().toString().equals(value)) {
                    return currentRadioButton.getId();
                }
            }
        }
        return -1; // Return -1 if the value is not found
    }

    // Validate the form
    private boolean validateHike(String name, String location, String length, String parking, String difficulty, String desc, String guide) {
        boolean result = true;
        if (name == null || name.trim().length() == 0) {
            binding.hikeName.setError("Name is required");
            result = false;
        }
        if (location == null || location.trim().length() == 0) {
            binding.hikeLocation.setError("Location is required");
            result = false;
        }

        if (!isNum(length)) {
            binding.hikeLength.setError("Length must be a number");
            result = false;
        }
        if (length == null || length.trim().length() == 0) {
            binding.hikeLength.setError("Length is required");
            result = false;
        }
        if (parking == null || parking.trim().length() == 0) {
            result = false;
        }
        if (difficulty == null || difficulty.trim().length() == 0) {
            result = false;
        }

        return result;
    }

    private boolean isNum(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}