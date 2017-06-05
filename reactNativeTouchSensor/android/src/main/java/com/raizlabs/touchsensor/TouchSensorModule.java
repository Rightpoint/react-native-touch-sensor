package com.raizlabs.touchsensor;

import android.Manifest;
import android.app.FragmentManager;
import android.app.Activity;
import android.content.pm.PackageManager;

import android.hardware.fingerprint.FingerprintManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;




public class TouchSensorModule extends ReactContextBaseJavaModule {

    private static final String DIALOG_FRAGMENT_TAG = "fingerprintAuthenticationFragment";

    FingerprintManagerCompat fingerprint;
    ReactApplicationContext context;
    CancellationSignal cancel;

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
        Boolean hasPermissions = ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED;
        Boolean hasFingerprints = fingerprint.hasEnrolledFingerprints();
        Boolean hardwareSupported = fingerprint.isHardwareDetected();
    }

    @ReactMethod
    public void hasPermissions(Promise promise) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {
            promise.resolve(null);
        }
        else {
            promise.reject("Error", "No Permissions");
        }
    }

    @ReactMethod
    public void hardwareSupported(Promise promise) {
        if (fingerprint.isHardwareDetected()) {
            promise.resolve(null);
        }
        else {
            promise.reject("Error", "Hardware not supported");
        }
    }

    @ReactMethod
    public void hasFingerprints(Promise promise) {
        if (fingerprint.hasEnrolledFingerprints()) {
            promise.resolve(null);
        }
        else {
            promise.reject("Error", "No Fingerprints");
        }
    }

    @ReactMethod
    public void authenticate(String message, Promise promise) {
        FingerprintAuthenticationDialogFragment fragment
                = new FingerprintAuthenticationDialogFragment();
        fragment.setStage(
                    FingerprintAuthenticationDialogFragment.Stage.FINGERPRINT);
        Activity activity = this.context.getCurrentActivity();
        FragmentManager manager = activity.getFragmentManager();
        fragment.show(manager, DIALOG_FRAGMENT_TAG);
//        fingerprint.authenticate(null, 0, cancel, new AuthCallback(promise), null);
    }

    public class AuthCallback extends FingerprintManagerCompat.AuthenticationCallback {
        Promise promise;

        public AuthCallback(Promise promise) {
            this.promise = promise;
        }

        /**
         * Called when an unrecoverable error has been encountered and the operation is complete.
         * No further callbacks will be made on this object.
         * @param errMsgId An integer identifying the error message
         * @param errString A human-readable error string that can be shown in UI
         */
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            promise.reject(Integer.toString(errMsgId), errString.toString());
            promise = null;
        }

        /**
         * Called when a recoverable error has been encountered during authentication. The help
         * string is provided to give the user guidance for what went wrong, such as
         * "Sensor dirty, please clean it."
         * @param helpMsgId An integer identifying the error message
         * @param helpString A human-readable string that can be shown in UI
         */
        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
        }

        /**
         * Called when a fingerprint is recognized.
         * @param result An object containing authentication-related data
         */
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            promise.resolve(null);
            promise = null;
        }

        /**
         * Called when a fingerprint is valid but not recognized.
         */
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            promise.reject("Error", "Bad Fingerprint");
            promise = null;
        }
    }
}
