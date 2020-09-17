
github  https://github.com/90Mark/react-native-file-select-mk

本文档，适用于ReactNative 项目

ios 配置：


    info.plist 中需要添加
    
    UIFileSharingEnabled 使iTunes分享你文件夹内的内容
    LSSupportsOpeningDocumentsInPlace 本地文件的获取权限

    这两个需要设置yes



   




使用方法:

安装


    npm install react-native-file-select-mk --save
    or
    yarn add react-native-file-select-mk

导入

    import RNFileSelect from 'react-native-file-select-mk'


 应用

     RNFileSelect.showFileList((res) => {
      console.log('path', res)
      if (res.type === 'cancel') {
        //用户取消
      } else if (res.type === 'path') {
        // 选中单个文件
      } else if (res.type === 'paths') {
          // 选中多个文件 看管理器支持情况目前采用默认的，只有会调用path
      }
    })

  
