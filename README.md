# netty_study
学习netty的NIO编程，包含http，websocket，socket例子，protobuf、thrift和gRPC等

# 使用protobuf
## 1.到['protocolbuffers'](https://github.com/protocolbuffers/protobuf/releases)下载
windows下的编译器 protoc-3.9.0-win64.zip，以及特定的语言编码器protobuf-java-3.9.0.zip
## 2.配置编译器 protoc-3.9.0-win64.zip的环境变量
## 3.到maven仓库里查找protobuf相关插件，引入Gradle中管理
    dependencies {
        //testCompile group: 'junit', name: 'junit', version: '4.12'
        compile group: 'io.netty', name: 'netty-all', version: '4.1.10.Final'
        compile group: 'com.google.protobuf', name: 'protobuf-java', version: '3.3.1'
        //针对移动端
        compile group: 'com.google.protobuf', name: 'protobuf-java-util', version: '3.3.1'
    }

