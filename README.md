# react-native-touch-sensor

Bridging for accessing the Touch Sensor on both iOS and Android using TouchID and Android Fingerprint

# Installation

`npm install -s react-native-touch-sensor`
`react-native link react-native-touch-sensor`

# Android Setup

Be sure to add the Fingerprint permission to your `AndroidManifest.xml` file

`<uses-permission android:name="android.permission.USE_FINGERPRINT" />`

Currently `react-native-touch-sensor` requires Android SDK 23+ to work.  Update this in your `app.gradle` file

# Example
```
import React, { Component } from 'react'
import { View, Text, Button } from 'react-native'

import Touch from 'react-native-touch-sensor'

export default class TouchExample extends Component {


    _isSupported() {
        Touch.isSupported()
            .then( () => alert('Android fingerprint supported'))
            .catch( (error) => alert(`unsupported: ${error}`))
    }
    _authenticatePressed() {
        Touch.authenticate("To test out the app")
            .then( () => alert('authenticated') )
            .catch( (error) => alert(`Failed: ${error}`) )
    }

    render() {
        return (
            <View>
                <Text>Check to see if all conditions are met to use Fingerprint</Text>
                <Button
                    title="IsSupported()"
                    onPress={() => this._isSupported()}
                    />
                <Text>Begins Authentication process</Text>
                <Button
                    title="Authenticate()"
                    onPress={() => this._authenticatePressed()}
                    />
            </View>
        );
    }
}

```

# Reference:
    TouchID: https://github.com/naoufal/react-native-touch-id
    AndroidFingerprint: https://github.com/googlesamples/android-FingerprintDialog
                        https://github.com/jariz/react-native-fingerprint-android
                        https://material.io/guidelines/patterns/fingerprint.html#fingerprint-enrollment
