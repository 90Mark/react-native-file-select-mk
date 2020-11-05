package com.mk.RNFileSelect;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class RNFileSelectModule extends ReactContextBaseJavaModule {

  private Promise callbackPromise;
  private final ReactApplicationContext mReactContext;
  public static final int REQUEST_CODE_GET_FILE    = 20001;

  public RNFileSelectModule(ReactApplicationContext context) {
    super(context);
    mReactContext = context;
    getReactApplicationContext().addActivityEventListener(new ActivityEventListener());
  }

  @Override
  public String getName() {
    return "RNFileSelect";
  }

  @ReactMethod
  public void showFileList(final Promise p) {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("*/*");
    //intent.setType(“image/*”);//选择图片
    //intent.setType(“audio/*”); //选择音频
    //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
    //intent.setType(“video/*;image/*”);//同时选择视频和图片
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    this.callbackPromise = p;
    try {
//      startActivityForResult(Intent.createChooser(intent, "选择文件"), REQUEST_CODE_GET_FILE);
      startActivityForResult(getReactApplicationContext().getCurrentActivity(), intent, REQUEST_CODE_GET_FILE, null);
    } catch (android.content.ActivityNotFoundException ex) {
      p.reject(null, "showfileList fail");
    }
  }

  private class ActivityEventListener implements com.facebook.react.bridge.ActivityEventListener {

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
      if (resultCode != Activity.RESULT_OK) {
        if (callbackPromise != null) {
          WritableMap response = Arguments.createMap();
          response.putString("type", "cancel");
          callbackPromise.resolve(response);
          callbackPromise = null;
        }
        return;
      }
      switch (requestCode)
      {
        case REQUEST_CODE_GET_FILE:
          String filePath = getRealPathFromURI(data.getData());
          if (callbackPromise != null && filePath != null) {
             WritableMap response = Arguments.createMap();
               response.putString("type", "path");
               response.putString("path", filePath);
               callbackPromise.resolve(response);
            }
          callbackPromise = null;
        break;
       }

    }

    @Override
    public void onNewIntent(Intent intent) {

    }
  }

  private String getRealPathFromURI(Uri uri) {
    try {
      Cursor returnCursor = mReactContext.getContentResolver().query(uri, null, null, null, null);
      int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
      returnCursor.moveToFirst();
      String name = (returnCursor.getString(nameIndex));
      File file = new File(mReactContext.getFilesDir(), name);
      InputStream inputStream = mReactContext.getContentResolver().openInputStream(uri);
      FileOutputStream outputStream = new FileOutputStream(file);
      int read = 0;
      int maxBufferSize = 1 * 1024 * 1024;
      int bytesAvailable = inputStream.available();

      int bufferSize = Math.min(bytesAvailable, maxBufferSize);

      final byte[] buffers = new byte[bufferSize];
      while ((read = inputStream.read(buffers)) != -1) {
        outputStream.write(buffers, 0, read);
      }
      returnCursor.close();
      inputStream.close();
      outputStream.close();
      return file.getPath();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}

