//
//  DocumentPickerVC.m
//  RNFileSelect
//
//  Created by Mark on 2020/9/15.
//  Copyright © 2020 Mark. All rights reserved.
//

#import "DocumentPicker.h"

@interface DocumentPicker()<UIDocumentPickerDelegate>

@end

@implementation DocumentPicker

static id instance;
#pragma mark - 单例
+ (instancetype)sharedDocumentPicker {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        if (instance == nil) {
            instance = [[self alloc] init];
        }
    });
    return instance;
}

- (void)showFileList:(CallBack)resp;
{
    self.callback = resp;
    NSArray *documentTypes = @[@"public.content",
                                @"public.text",
                                @"public.source-code",
                                @"public.image",
                                @"public.audiovisual-content",
                                @"com.adobe.pdf",
                                @"com.apple.keynote.key",
                                @"com.microsoft.word.doc",
                                @"com.microsoft.excel.xls",
                                @"com.microsoft.powerpoint.ppt"];
            
    UIDocumentPickerViewController *pickerVC = [[UIDocumentPickerViewController alloc]initWithDocumentTypes:documentTypes inMode:UIDocumentPickerModeOpen];
    pickerVC.delegate = self;
        
    UIWindow *win = [[UIApplication sharedApplication].windows objectAtIndex:0];
    [win.rootViewController presentViewController:pickerVC animated:YES completion:nil];
}
#pragma mark - UIDocumentPickerDelegate

- (void)documentPicker:(UIDocumentPickerViewController *)controller didPickDocumentsAtURLs:(NSArray <NSURL *>*)urls API_AVAILABLE(ios(11.0));
{
    NSMutableArray * muArr = [NSMutableArray arrayWithCapacity:1];
    for (NSURL * url in urls) {
        BOOL canAccessingResource = [url startAccessingSecurityScopedResource];
        if(canAccessingResource) {
            NSFileCoordinator *fileCoordinator = [[NSFileCoordinator alloc] init];
            NSError *error;
            [fileCoordinator coordinateReadingItemAtURL:url options:0 error:&error byAccessor:^(NSURL *newURL) {
                NSData *fileData = [NSData dataWithContentsOfURL:newURL];
                NSString *fileName = [[[url absoluteString] componentsSeparatedByString:@"/"] lastObject];
                NSString *desFileName = [NSTemporaryDirectory() stringByAppendingPathComponent:fileName];
                [fileData writeToFile:desFileName atomically:YES];
                [muArr addObject:desFileName];
            }];
            [url stopAccessingSecurityScopedResource];
            if (error) {
                [self callBack:@{@"type":@"error"}];
            }
        } else {
            [self callBack:@{@"type":@"error"}];
        }
    }
    if(urls.count==1){
        [self callBack:@{@"type":@"path",@"path":muArr[0]}];
    }else{
        [self callBack:@{@"type":@"paths",@"paths":muArr}];
    }
}
- (void)documentPickerWasCancelled:(UIDocumentPickerViewController *)controller;{
    [self callBack:@{@"type":@"cancel"}];
}

- (void)documentPicker:(UIDocumentPickerViewController *)controller didPickDocumentAtURL:(NSURL *)url{
    NSString * urlStr = [url absoluteString];
    [self callBack:@{@"type":@"path",@"path":urlStr}];
}


-(void)callBack:(NSDictionary *)dic
{
    if (self.callback) {
        self.callback(dic);
    }
}
@end
