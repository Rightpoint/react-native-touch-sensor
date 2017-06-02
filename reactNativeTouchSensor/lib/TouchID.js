/**
 * @flow
 */

import { NativeModules } from 'react-native'

const NativeModule = NativeModules.TouchSensor

export default {

    /**
     * Checks to see if the current setup is supported on the device given current settings.
     */
    isSupported() {
        return NativeModule.isSupported()
    },



    /**
     * Asks the user for their fingerprint and returns the result.
     * @param {* The reason why you are asking for the fingerprint.} reason 
     */
    authenticate(reason: String) {
        reason = reason || ' '
        return NativeModule.authenticate(reason)
    },

    /**
     * Android Methods
     */
        
     hasPermission() {
         return NativeModule.hasPermissions()
     },

     hardwareSupported() {
         return NativeModule.hardwareSupported()
     },

     hasFingerprints() {
         return NativeModule.hasFingerprints()
     }

}
