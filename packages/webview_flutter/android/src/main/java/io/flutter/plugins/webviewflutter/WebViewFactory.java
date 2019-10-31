// Copyright 2018 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.webviewflutter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import java.util.Map;

public final class WebViewFactory extends PlatformViewFactory implements PluginRegistry.ActivityResultListener {
  private final BinaryMessenger messenger;
  private final View containerView;
  PluginRegistry.Registrar registrar;
  FlutterWebView flutterWebView;
  WebViewFactory(BinaryMessenger messenger, View containerView, PluginRegistry.Registrar registrar) {
    super(StandardMessageCodec.INSTANCE);
    this.messenger = messenger;
    this.containerView = containerView;
    this.registrar = registrar;
  }

  @SuppressWarnings("unchecked")
  @Override
  public PlatformView create(Context context, int id, Object args) {
    Map<String, Object> params = (Map<String, Object>) args;
    flutterWebView = new FlutterWebView(registrar.activity(),context, messenger, id, params, containerView);
    registrar.addActivityResultListener(this);
    return flutterWebView;
  }


  @Override
  public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
    if (flutterWebView != null ) {
      return flutterWebView.handleResult(requestCode, resultCode, data);
    }
    return false;
  }
}
