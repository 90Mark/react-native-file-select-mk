import { NativeModules } from 'react-native'

var { RNFileSelect } = NativeModules

export default {
  showFileList
}

function showFileList (callback) {
  RNFileSelect.showFileList().then((res) => {
    callback(res)
  }).catch((err) => {
    callback(null)
  })
}
