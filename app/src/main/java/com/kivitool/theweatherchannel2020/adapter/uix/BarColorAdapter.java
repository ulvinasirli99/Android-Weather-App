package com.kivitool.theweatherchannel2020.adapter.uix;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BarColorAdapter extends Window {

    public BarColorAdapter(Context context) {
        super(context);
    }

    @Override
    public void takeSurface(SurfaceHolder.Callback2 callback) {

    }

    @Override
    public void takeInputQueue(InputQueue.Callback callback) {

    }

    @Override
    public boolean isFloating() {
        return false;
    }

    @Override
    public void setContentView(int layoutResID) {

    }

    @Override
    public void setContentView(View view) {

    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {

    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {

    }

    @Nullable
    @Override
    public View getCurrentFocus() {
        return null;
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return null;
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    public void setTitleColor(int textColor) {

    }

    @Override
    public void openPanel(int featureId, KeyEvent event) {

    }

    @Override
    public void closePanel(int featureId) {

    }

    @Override
    public void togglePanel(int featureId, KeyEvent event) {

    }

    @Override
    public void invalidatePanelMenu(int featureId) {

    }

    @Override
    public boolean performPanelShortcut(int featureId, int keyCode, KeyEvent event, int flags) {
        return false;
    }

    @Override
    public boolean performPanelIdentifierAction(int featureId, int id, int flags) {
        return false;
    }

    @Override
    public void closeAllPanels() {

    }

    @Override
    public boolean performContextMenuIdentifierAction(int id, int flags) {
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void setBackgroundDrawable(Drawable drawable) {

    }

    @Override
    public void setFeatureDrawableResource(int featureId, int resId) {

    }

    @Override
    public void setFeatureDrawableUri(int featureId, Uri uri) {

    }

    @Override
    public void setFeatureDrawable(int featureId, Drawable drawable) {

    }

    @Override
    public void setFeatureDrawableAlpha(int featureId, int alpha) {

    }

    @Override
    public void setFeatureInt(int featureId, int value) {

    }

    @Override
    public void takeKeyEvents(boolean get) {

    }

    @Override
    public boolean superDispatchKeyEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean superDispatchKeyShortcutEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean superDispatchTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean superDispatchTrackballEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean superDispatchGenericMotionEvent(MotionEvent event) {
        return false;
    }

    @NonNull
    @Override
    public View getDecorView() {
        return null;
    }

    @Override
    public View peekDecorView() {
        return null;
    }

    @Override
    public Bundle saveHierarchyState() {
        return null;
    }

    @Override
    public void restoreHierarchyState(Bundle savedInstanceState) {

    }

    @Override
    protected void onActive() {

    }

    @Override
    public void setChildDrawable(int featureId, Drawable drawable) {

    }

    @Override
    public void setChildInt(int featureId, int value) {

    }

    @Override
    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void setVolumeControlStream(int streamType) {

    }

    @Override
    public int getVolumeControlStream() {
        return 0;
    }

    @Override
    public int getStatusBarColor() {
        return 0;
    }

    @Override
    public void setStatusBarColor(int color) {

    }

    @Override
    public int getNavigationBarColor() {
        return 0;
    }

    @Override
    public void setNavigationBarColor(int color) {

    }

    @Override
    public void setDecorCaptionShade(int decorCaptionShade) {

    }

    @Override
    public void setResizingCaptionDrawable(Drawable drawable) {

    }
}
