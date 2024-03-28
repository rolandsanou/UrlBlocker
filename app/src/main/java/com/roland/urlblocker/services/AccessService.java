package com.roland.urlblocker.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.provider.Browser;
import android.util.Log;
import android.util.Patterns;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.roland.urlblocker.database.UrlLocalDatabase;
import com.roland.urlblocker.models.UrlModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

public class AccessService extends AccessibilityService {
    private static String TAG = "AcccessService";
    private static String _currentHomePackage = "";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        try {
            String appPackageName, eventText;
            if (AccessibilityEvent.eventTypeToString(event.getEventType()).contains("WINDOW")) {
                AccessibilityNodeInfo nodeInfo = event.getSource();
                if (nodeInfo != null) {
                    dfs(nodeInfo);
                    nodeInfo.recycle();
                }
            }
        }catch (Exception e){
            Log.d("C-Prot: ", e.getMessage());
        }
    }

    public void dfs(AccessibilityNodeInfo info) {
        if (info.getText() != null && info.getText().length() > 0) {
            Log.i(TAG, String.valueOf(info.getClassName()));
            if (info.getClassName().toString().contains("EditText") ||
                    info.getClassName().toString().contains("TextView")) {
                String inputText = info.getText().toString();
                System.out.println("ACCESSIBILITY SERVICE : "+inputText);
                //blockedList(extractUrl(inputText));
                if(blockedList(extractUrl(inputText))){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ari/"));
                    intent.setPackage("com.android.chrome");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        Log.d("MainRedirect", "Exception = " + ex.toString());
                    }
                }
            }
        }
    }

    private List<String> extractUrl(String text){
        // Create a list to store extracted URLs
        List<String> urls = new ArrayList<>();
        // Use Patterns.WEB_URL to create a URL pattern matcher
        Matcher matcher = Patterns.WEB_URL.matcher(text);

        while (matcher.find()) {
            String url = matcher.group();
            Log.d(TAG, "Detected URL: " + url);
            urls.add(url);
        }

        return urls;
    }

    private boolean blockedList(List<String>extractedUrls){
        List<String> databaseUrls = UrlLocalDatabase.getInstance(this).UrlDatabaseDao().getBlockedUrlsList();
        if(!databaseUrls.isEmpty()){
            for (String item:
                    extractedUrls) {
                for (String url:
                        databaseUrls) {
                    Log.d(TAG, "Detected URL: " + item+ " vs Database URL: "+url);
                    if(item.equalsIgnoreCase(url) || item.toLowerCase().contains(url.toLowerCase())){
                        Log.d(TAG, "Lock In");
                        return true;
                    }
                }
            }
        }

        return false;
    }


    @Override
    public void onInterrupt() {

    }

    @Override
    public void onServiceConnected(){
        System.out.println("onServiceConnected");
        Intent intentLauncher = new Intent(Intent.ACTION_MAIN);
        intentLauncher.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intentLauncher, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null) {
            _currentHomePackage = resolveInfo.activityInfo.packageName;
        }
    }

}
