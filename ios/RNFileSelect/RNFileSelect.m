//
//  RNFileSelect.m
//  RNFileSelect
//
//  Created by Mark on 2020/9/15.
//  Copyright © 2020 Mark. All rights reserved.
//

#import "RNFileSelect.h"

#if __has_include("RCTUtils.h")
    #import "RCTUtils.h"
#else
    #import <React/RCTUtils.h>
#endif

#import "DocumentPicker.h"

@implementation RNFileSelect

RCT_EXPORT_MODULE();

+ (BOOL)requiresMainQueueSetup {
    return YES;
}

RCT_EXPORT_METHOD(showFileList:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    dispatch_async(dispatch_get_main_queue(), ^{
        [[DocumentPicker sharedDocumentPicker] showFileList:^(NSDictionary * resp) {
               if (resp) {
                   NSLog(@"%@",resp);
                   resolve(resp);
               }else{
                   reject(nil, @"showfileList fail", nil);
               }
           }];
    });
}

@end
