package com.mk.NSFileSelect;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.media.AudioManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.ExceptionsManagerModule;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import android.util.Log;

public class NSFileSelectModule extends ReactContextBaseJavaModule implements AudioManager.OnAudioFocusChangeListener {
  Map<Double, MediaPlayer> playerPool = new HashMap<>();
  ReactApplicationContext context;
  final static Object NULL = null;
  String category;
  Boolean mixWithOthers = true;
  Double focusedPlayerKey;
  Boolean wasPlayingBeforeFocusChange = false;

  public NSFileSelectModule(ReactApplicationContext context) {
    super(context);
  }

  @Override
  public String getName() {
    return "NSFileSelect";
  }

  @ReactMethod
  public void log(String msg) {
    	Log.w(msg);
  }
}
