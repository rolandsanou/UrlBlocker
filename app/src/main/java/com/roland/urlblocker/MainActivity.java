package com.roland.urlblocker;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.roland.urlblocker.adapter.UrlAdapter;
import com.roland.urlblocker.database.UrlLocalDatabase;
import com.roland.urlblocker.helpers.SwipeToDelete;
import com.roland.urlblocker.models.UrlModel;
import com.roland.urlblocker.services.AccessService;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final int SETTINGS_ACCESSIBILITY = 1;
    private TextInputLayout _urlLayout;
    private TextInputEditText _urlEditText;
    private RecyclerView _mainRecyclerView;
    private Button _addButton;
    private ArrayList<UrlModel> _blockedList;
    private UrlAdapter _adapter;
    private boolean _isAppUsagePermissionGrantend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _urlLayout = findViewById(R.id.urlLayout);
        _urlEditText = findViewById(R.id.urlTextInput);
        _addButton = findViewById(R.id.addButton);
        _mainRecyclerView = findViewById(R.id.browserHistoryUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            _isAppUsagePermissionGrantend = isAppUsagePermissionGrantend();
        }

        if(!_isAppUsagePermissionGrantend){
            showBottomSheetDialog(getString(R.string.accessibility_perm_title), getString(R.string.activity_accessibility_permission_description), SETTINGS_ACCESSIBILITY);
        }

        EmptyText(_urlLayout, _urlEditText);
        _blockedList = (ArrayList<UrlModel>) UrlLocalDatabase.getInstance(this).UrlDatabaseDao().getBlockedList();
        InstanceRecycler();
        addUrl();
    }

    private void InstanceRecycler(){
        if(!_blockedList.isEmpty()){
            _adapter = new UrlAdapter(_blockedList, this);
            _mainRecyclerView.setAdapter(_adapter);
            _mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete(_adapter, getDrawable(R.drawable.ic_delete), this));
            itemTouchHelper.attachToRecyclerView(_mainRecyclerView);
        }
    }

    private void addUrl(){
        _addButton.setOnClickListener(v -> {
            String url = _urlEditText.getEditableText().toString();
            if(!url.isEmpty() && isWebURL(url)){
                UrlModel model = new UrlModel();
                model.setId(UUID.randomUUID());
                model.setName(url);
                model.setDate(System.currentTimeMillis());
                _blockedList.add(model);
                if(_adapter != null){
                    _adapter.notifyDataSetChanged();
                }else{
                    InstanceRecycler();
                }

                UrlLocalDatabase.getInstance(this).UrlDatabaseDao().insertUrl(model);
                Toast.makeText(this, getString(R.string.successfull_added), Toast.LENGTH_SHORT).show();
                _urlEditText.setText(" ");
            } else if (!isWebURL(url)) {
                Toast.makeText(this, getString(R.string.misform_url_msg_txt), Toast.LENGTH_SHORT).show();
                _urlLayout.setError(getString(R.string.misform_url_msg_txt));
            }
        });
    }

    public static boolean isWebURL(String input) {
        // Use the Android library pattern to check for a web URL
        return Patterns.WEB_URL.matcher(input).matches();
    }

    private void EmptyText(TextInputLayout textInputLayout, TextInputEditText textInputEditText){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textInputLayout.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(textInputEditText.getEditableText().toString().isEmpty()){
                    textInputLayout.setError(getString(R.string.error_empty));
                }else{
                    textInputLayout.setError(null);
                }
            }
        });
    }

    private boolean isAppUsagePermissionGrantend() {
        ComponentName compName = new ComponentName(getApplicationContext(), AccessService.class);
        String flatName = compName.flattenToString();
        String enabledList = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        return enabledList != null && enabledList.contains(flatName);
    }

    private void showBottomSheetDialog(String title, String description, int type_alert) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.access_bottom_sheet);
        Button permissionAccess = bottomSheetDialog.findViewById(R.id.permission_access_button);
        Button deniedAccess = bottomSheetDialog.findViewById(R.id.cancel_button);
        TextView titleMessage = bottomSheetDialog.findViewById(R.id.title);
        TextView messageDescription = bottomSheetDialog.findViewById(R.id.description);
        titleMessage.setText(title);
        messageDescription.setText(description);
        bottomSheetDialog.show();
        permissionAccess.setOnClickListener(v -> {
            if(type_alert == SETTINGS_ACCESSIBILITY)
            {
                startActivity( new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                bottomSheetDialog.dismiss();
            }
        });

        deniedAccess.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });
    }
}