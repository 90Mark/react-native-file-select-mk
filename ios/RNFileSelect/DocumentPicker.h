//
//  DocumentPickerVC.h
//  RNFileSelect
//
//  Created by Mark on 2020/9/15.
//  Copyright © 2020 Mark. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

typedef void(^CallBack)(NSDictionary *responseDic);

@interface DocumentPicker : NSObject

@property (copy, nonatomic) CallBack callback;

+ (instancetype)sharedDocumentPicker;

- (void)showFileList:(CallBack)resp;


@end

NS_ASSUME_NONNULL_END
