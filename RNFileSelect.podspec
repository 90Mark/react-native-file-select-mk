require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name                = "RNFileSelect"
  s.version             = package['version']
  s.summary             = package['description']
  s.homepage            = package['homepage']
  s.license             = package['license']
  s.author              = package['author']
  s.source              = { :git => 'https://github.com/90Mark/react-native-file-select-mk.git', :tag => "v#{s.version}" }
  s.default_subspec     = 'Core'
  s.requires_arc        = true
  s.platform            = :ios, "9.0"

  s.dependency 'React'

  s.subspec 'Core' do |ss|
    ss.source_files     = "ios/*.{h,m}"
  end

end
