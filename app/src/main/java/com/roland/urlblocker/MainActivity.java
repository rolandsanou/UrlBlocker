package com.roland.urlblocker;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.roland.urlblocker.adapter.UrlAdapter;
import com.roland.urlblocker.database.UrlLocalDatabase;
import com.roland.urlblocker.helpers.SwipeToDelete;
import com.roland.urlblocker.models.UrlModel;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout _urlLayout;
    private TextInputEditText _urlEditText;
    private RecyclerView _mainRecyclerView;
    private Button _addButton;
    private UrlAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _urlLayout = findViewById(R.id.urlLayout);
        _urlEditText = findViewById(R.id.urlTextInput);
        _addButton = findViewById(R.id.addButton);
        _mainRecyclerView = findViewById(R.id.browserHistoryUrl);
        EmptyText(_urlLayout, _urlEditText);
        addUrl();
        InstanceRecycler();
    }

    private void InstanceRecycler(){
        ArrayList<UrlModel> blockedList = (ArrayList<UrlModel>) UrlLocalDatabase.getInstance(this).UrlDatabaseDao().getBlockedList();
        if(!blockedList.isEmpty()){
            _adapter = new UrlAdapter(blockedList, this);
            _mainRecyclerView.setAdapter(_adapter);
            _mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete(_adapter, getDrawable(R.drawable.ic_delete), this));
            itemTouchHelper.attachToRecyclerView(_mainRecyclerView);
        }
    }

    private void addUrl(){
        _addButton.setOnClickListener(v -> {
            String url = _urlEditText.getEditableText().toString();
            if(!url.isEmpty()){
                UrlModel model = new UrlModel();
                model.setId(UUID.randomUUID());
                model.setName(url);
                model.setDate(System.currentTimeMillis());
                UrlLocalDatabase.getInstance(this).UrlDatabaseDao().insertUrl(model);
                Toast.makeText(this, getString(R.string.successfull_added), Toast.LENGTH_SHORT).show();
                _urlEditText.setText(" ");
            }
        });
    }

    private void EmptyText(TextInputLayout textInputLayout, TextInputEditText textInputEditText){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(textInputEditText.getEditableText().toString().isEmpty()){
                    textInputLayout.setError(getString(R.string.error_empty));
                }
            }
        });
    }
}