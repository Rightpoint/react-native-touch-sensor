package com.raizlabs.touchsensor;

import android.Manifest;
import android.app.FragmentManager;
import android.app.Activity;
import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;


public class TouchSensorModule extends ReactContextBaseJavaModule
    implements FingerprintAuthenticationDialogFragment.FingerprintCallback {

    private static final String DIALOG_FRAGMENT_TAG = "fingerprintAuthenticationFragment";

    FingerprintManagerCompat fingerprint;
    ReactApplicationContext context;
    Promise authPromise;

    public TouchSensorModule(ReactApplicationContext reactContext) {
        super(reactContext);

        this.context = reactContext;
        this.fingerprint = FingerprintManagerCompat.from(reactContext);
    }

    @Override
    public String getName() {
        return "TouchSensor";
    }

    @ReactMethod
    public void isSupported(Promise promise) {
        String errorMessage = null;
        String errorTitle = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {
            errorMessage = this.context.getString(R.string.permission_error_message);
            errorTitle = this.context.getString(R.string.permission_error_title);
            promise.reject(errorTitle, errorMessage);
            return;
        }

        if (fingerprint.isHardwareDetected()) {
            errorTitle = this.context.getString(R.string.hardware_error_title);
            errorMessage = this.context.getString(R.string.hardware_error_message);
            promise.reject(errorTitle, errorMessage);
            return;
        }

        if (fingerprint.hasEnrolledFingerprints()) {
            errorTitle = this.context.getString(R.string.fingerprint_error_title);
            errorMessage = this.context.getString(R.string.fingerprint_error_message);
            promise.reject(errorTitle, errorMessage);
            return;
        }
        promise.resolve(null);
    }

    @ReactMethod
    public void hasPermissions(Promise promise) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {
            promise.resolve(null);
        }
        else {
            String errorMessage = this.context.getString(R.string.permission_error_message);
            String errorTitle = this.context.getString(R.string.permission_error_title);
            promise.reject(errorTitle, errorMessage);
        }
    }

    @ReactMethod
    public void hardwareSupported(Promise promise) {
        if (fingerprint.isHardwareDetected()) {
            promise.resolve(null);
        }
        else {
            String errorTitle = this.context.getString(R.string.hardware_error_title);
            String errorMessage = this.context.getString(R.string.hardware_error_message);
            promise.reject(errorTitle, errorMessage);

        }
    }

    @ReactMethod
    public void hasFingerprints(Promise promise) {
        if (fingerprint.hasEnrolledFingerprints()) {
            promise.resolve(null);
        }
        else {
            String errorTitle = this.context.getString(R.string.fingerprint_error_title);
            String errorMessage = this.context.getString(R.string.fingerprint_error_message);
            promise.reject(errorTitle, errorMessage);
        }
    }

    @ReactMethod
    public void authenticate(String message, Promise promise) {
        FingerprintAuthenticationDialogFragment fragment
                = new FingerprintAuthenticationDialogFragment();
        fragment.callback = this;
        fragment.setStage(
                    FingerprintAuthenticationDialogFragment.Stage.FINGERPRINT);
        Activity activity = this.context.getCurrentActivity();
        FragmentManager manager = activity.getFragmentManager();
        fragment.show(manager, DIALOG_FRAGMENT_TAG);
        this.authPromise = promise;
    }

    public void authenticateSuccess() {
        this.authPromise.resolve(null);
    }

    public void authenticateFailed() {
        this.authPromise.reject(
                this.context.getString(R.string.generic_error_title),
                this.context.getString(R.string.fingerprint_not_recognized));
    }

    public void authenticateCancelled() {
        this.authPromise = null;
    }
}
