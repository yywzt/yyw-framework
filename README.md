# Framework

Framework 是一个基于 Spring Boot 的项目，旨在提供一系列常用的功能模块，以简化开发过程。该项目包括以下模块：

- **framework-api**: 包含公共的实体类、枚举类、错误码等，用于提供基础的数据结构和常量定义。
- **framework-data-dict**: 定义数据字典的API，并提供内存数据字典的实现类，用于管理和使用数据字典。
- **framework-exception**: 实现了 Spring Boot 的全局异常捕获定义，用于统一处理项目中的异常情况。
- **framework-log**: 提供了一个自定义的 Spring Boot Starter，通过配置文件启用请求详细日志打印功能。同时，预留了扩展接口，以支持后续对日志数据进行个性化处理。
- **framework-parent**: 是一个父级 POM，用于统一管理依赖版本，确保各个模块之间的兼容性和一致性。
- **framework-util**: 包含了一系列常用的工具类，用于提供各种实用的功能方法。

## 技术栈

- Spring Boot

## 使用方法

1. 将所需的模块添加到您的项目依赖中。
2. 根据各个模块的说明，配置并使用相应的功能。

## 模块结构

```
cssCopy code
framework
│
├── framework-api
│   ├── src
│   └── ...
│
├── framework-data-dict
│   ├── src
│   └── ...
│
├── framework-exception
│   ├── src
│   └── ...
│
├── framework-log
│   ├── src
│   └── ...
│
├── framework-parent
│   ├── pom.xml
│   └── ...
│
└── framework-util
    ├── src
    └── ...
```

## 贡献

欢迎贡献代码、报告问题或提出建议！

## 许可证

该项目采用 MIT 许可证。
