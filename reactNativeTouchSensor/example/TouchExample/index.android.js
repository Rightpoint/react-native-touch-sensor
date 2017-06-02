/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  View,
  Text,
  Button
} from 'react-native';

import Touch from 'react-native-touch-sensor'

export default class TouchExample extends Component {
  

    _isSupported() {
        Touch.isSupported()
            .then( () => alert('Android fingerprint supported'))
            .catch( (error) => alert(`unsupported: ${error}`))
    }
    _hasPermission() {
        Touch.hasPermission()
            .then( () => alert('Permissions accepted'))
            .catch( (error) => alert(`unsupported: ${error}`))
    }
    
    _hardwareSupported() {
        Touch.hardwareSupported()
            .then( () => alert('Hardware supports it'))
            .catch( (error) => alert(`unsupported: ${error}`))
    }

    _hasFingerprints() {
        Touch.hasFingerprints()
            .then( () => alert('User Has fingerprints'))
            .catch( (error) => alert(`no fingerprints: ${error}`))
    }

    _authenticatePressed() {
        Touch.authenticate("To test out the app")
            .then( () => alert('authenticated') )
            .catch( (error) => alert(`Failed: ${error}`) )
    }


    render() {
        return (
            <View style={styles.container}>
                <Text>Check to see if all conditions are met to use Fingerprint</Text>
                <Button 
                    title="IsSupported()"
                    onPress={() => this._isSupported()}
                    />
                <Text>Check to see if the user has enabled system permissions</Text>
                <Button 
                    title="HasPermissions()"
                    onPress={() => this._hasPermission()}
                    />
                <Text>Checks The hardware support</Text>
                <Button 
                    title="HardwareSupported()"
                    onPress={() => this._hardwareSupported()}
                    />
                <Text>Checks if the user has fingerprints on their device</Text>
                <Button 
                    title="HasFingerprints()"
                    onPress={() => this._hasFingerprints()}
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

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('TouchExample', () => TouchExample);
