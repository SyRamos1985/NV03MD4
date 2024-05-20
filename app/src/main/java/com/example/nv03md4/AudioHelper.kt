
package com.example.nv03md4;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.content.pm.PackageManager;

public class AudioHelper {

    private final AudioManager audioManager;
    private final Context appContext;

    public AudioHelper(Context context) {
        this.appContext = context;
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public boolean isAudioOutputAvailable(int deviceType) {
        PackageManager packageManager = appContext.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
            return false;
        }

        AudioDeviceInfo[] devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
        for (AudioDeviceInfo device : devices) {
            if (device.getType() == deviceType) {
                return true;
            }
        }
        return false;
    }
}