package com.ldh.cordova.appinstaller;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class InstallApp extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("Install".equals(action)) {
            String path = args.getString(0);
            this.install(path, callbackContext);
            return true;
        }
        return false;
    }

    private void install(String path, CallbackContext callbackContext) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(Uri.parse(path), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            cordova.getActivity().startActivity(intent);
            callbackContext.success(path);
        } catch(Exception e) {
            callbackContext.error(e.toString());
        }
    }

    private void version(String path, CallbackContext callbackContext) {
        try {
           PackageManager packageManager = this.cordova.getActivity().getPackageManager();
           callbackContext.success(packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionCode);
        } catch(Exception e) {
            callbackContext.error(e.toString());
        }
    }
}
