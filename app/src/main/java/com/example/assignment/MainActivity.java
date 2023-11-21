package com.example.assignment;


import android.app.AlertDialog;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.content.DialogInterface;
import android.content.Intent;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.assignment.databinding.ActivityMainBinding;
import com.example.assignment.db.AppDatabase;
import com.example.assignment.db.Dao.HikeDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.assignment.adapter.HikesAdapter;
import com.example.assignment.db.DatabaseHelper;
import com.example.assignment.db.entity.Hike;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private HikesAdapter hikesAdapter;
    private HikeDao dao;
    private ArrayList<Hike> hikeArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
//    private DatabaseHelper db;
AppDatabase db;
    ActivityResultLauncher<Intent> resultLauncher;
   private String parkingValue;
   private String difficultyValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
//        registerResult()
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Hikes Management App");
        RecyclerView recyclerView = binding.include.recyclerViewHikes;
//        db = new DatabaseHelper(this);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "hike-db")
                .allowMainThreadQueries()
                .build();
        dao = db.hikeDao();
        hikeArrayList.addAll(dao.getAll());
        hikesAdapter = new HikesAdapter(this, hikeArrayList, MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(hikesAdapter);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> addAndEditHikes(false, null, -1));
        setContentView(binding.getRoot());
    }

    public void addAndEditHikes(final boolean isUpdated,final Hike hike,final int position) {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("isUpdated", isUpdated);
        Bundle extras = new Bundle();
        extras.putSerializable("hike", hike);
        intent.putExtras(extras);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    // menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // inflate (render) the menu (from res/menu/menu_option.xml)
        getMenuInflater().inflate(R.menu.menu_option, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                List<Hike> searchResult = dao.findHikeByName(newText);
                hikesAdapter.updateList(searchResult);
                return false;
            }
        });
        return true;
    }



}