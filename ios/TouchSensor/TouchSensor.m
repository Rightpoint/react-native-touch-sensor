//
//  TouchSensor.m
//  reactNativeTouchSensor
//
//  Created by alex.rouse on 5/26/17.
//  Copyright © 2017 Raizlabs. All rights reserved.
//

#import "TouchSensor.h"
#import <React/RCTUtils.h>
#import <LocalAuthentication/LocalAuthentication.h>

@implementation TouchSensor

RCT_EXPORT_MODULE();

RCT_REMAP_METHOD(isSupported,
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
    LAContext *context = [[LAContext alloc] init];
    NSError *error;

    if ([context canEvaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics error:&error]) {
        resolve(nil);
    }
    else {
        reject(error.localizedDescription, error.localizedRecoverySuggestion, error);
    }
}

RCT_EXPORT_METHOD(authenticate:(NSString *)reason
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    LAContext *context = [[LAContext alloc] init];
    NSError *error;

    if ([context canEvaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics error:&error]) {
        [context evaluatePolicy:LAPolicyDeviceOwnerAuthentication localizedReason:reason reply:^(BOOL success, NSError * _Nullable error) {
            if (error) {
                reject(error.localizedDescription, error.localizedRecoverySuggestion, error);
            }
            else {
                resolve(nil);
            }
        }];
    }
    else {
        reject(error.localizedDescription, error.localizedRecoverySuggestion, error);
    }
}

@end
