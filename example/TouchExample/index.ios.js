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
  
    _supportedPressed() {
        Touch.isSupported()
            .then( () => alert('supported'))
            .catch( (error) => alert(`unsupported: ${error}`))
    }
    
    _authenticatePressed() {
        Touch.authenticate("To test out the app")
            .then( () => alert('authenticated') )
            .catch( (error) => alert(`Failed: ${error}`) )
    }


    render() {
        return (
            <View style={styles.container}>
                <Button 
                    title="IsSupported()"
                    onPress={() => this._supportedPressed()}
                    />
                <Text> space </Text>
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
