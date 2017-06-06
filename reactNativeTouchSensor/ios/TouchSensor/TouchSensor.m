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
        [context evaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics localizedReason:reason reply:^(BOOL success, NSError * _Nullable error) {
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

//RCT_EXPORT_METHOD(isSupported: (RCTResponseSenderBlock)callback)
//{
//  LAContext *context = [[LAContext alloc] init];
//  NSError *error;
//
//  if ([context canEvaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics error:&error]) {
//    callback(@[[NSNull null], @true]);
//
//  } else {
//    callback(@[RCTMakeError(@"RCTTouchIDNotSupported", nil, nil)]);
//    return;
//  }
//}
//
//RCT_EXPORT_METHOD(authenticate: (NSString *)reason
//                  callback: (RCTResponseSenderBlock)callback)
//{
//  LAContext *context = [[LAContext alloc] init];
//  NSError *error;
//
//  // Device has TouchID
//  if ([context canEvaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics error:&error]) {
//    // Attempt Authentification
//    [context evaluatePolicy:LAPolicyDeviceOwnerAuthenticationWithBiometrics
//            localizedReason:reason
//                      reply:^(BOOL success, NSError *error)
//     {
//       // Failed Authentication
//       if (error) {
//         NSString *errorReason;
//
//         switch (error.code) {
//           case LAErrorAuthenticationFailed:
//             errorReason = @"LAErrorAuthenticationFailed";
//             break;
//
//           case LAErrorUserCancel:
//             errorReason = @"LAErrorUserCancel";
//             break;
//
//           case LAErrorUserFallback:
//             errorReason = @"LAErrorUserFallback";
//             break;
//
//           case LAErrorSystemCancel:
//             errorReason = @"LAErrorSystemCancel";
//             break;
//
//           case LAErrorPasscodeNotSet:
//             errorReason = @"LAErrorPasscodeNotSet";
//             break;
//
//           case LAErrorTouchIDNotAvailable:
//             errorReason = @"LAErrorTouchIDNotAvailable";
//             break;
//
//           case LAErrorTouchIDNotEnrolled:
//             errorReason = @"LAErrorTouchIDNotEnrolled";
//             break;
//
//           default:
//             errorReason = @"RCTTouchIDUnknownError";
//             break;
//         }
//
//         NSLog(@"Authentication failed: %@", errorReason);
//         callback(@[RCTMakeError(errorReason, nil, nil)]);
//         return;
//       }
//
//       // Authenticated Successfully
//       callback(@[[NSNull null], @"Authenticat with Touch ID."]);
//     }];
//
//    // Device does not support TouchID
//  } else {
//    callback(@[RCTMakeError(@"RCTTouchIDNotSupported", nil, nil)]);
//    return;
//  }
//}

@end