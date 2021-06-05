<h1 align="center"> Zygote </h1> <br>

## Table of Contents

- [Introduction](#introduction)
- [Component](#component)
- [Principle](#principle)
- [Use](#use)
- [Contact](#contact)

## Introduction

Zygote is a rapid development framework for Android.

### Component

在Zygote框架中，组件分为五大类 ：基础组件(base开头) ，通用库组件(lib开头)，功能组件(fun开头)，业务组件(bus开头) ，App组件(module开头)

* **基础组件**,基础组件是框架中最底层的组件，为其他组件提供依赖支撑，它不能依赖任何第三方的库或组件。
naming convention ： base + component name
constraints： 不依赖任何其他组件和第三方库

* **通用库组件**,通用库组件即开发App中提供最基础功能的组件，这类组件在所有的App中都会使用并且与具体的业务无关，
App开发者不需要关心基础组件的实现也不应该任意修改该组件，只需要通过库组件提供的服务和接口来实现业务层的功能即可。
naming convention ：lib_+component name
constraints： 可以依赖于基础组件或者不依赖其他组件，可以依赖其他第三方库

* **功能组件**,功能组件提供与项目无关的功能模块，可以是具体的页面也可以特定某一领域的功能，功能组件可以依赖某一个基础
组件，但是不能依赖于业务组件。
naming convention ：fun_+component name
constraints： 可以依赖基础组件或者通用库组件，可以依赖第三方库

* **业务组件**,业务组件是App的组成部分与所开发的具体业务相关，App组件依赖于业务组件进行具体的业务开发，
业务组件的存在，为定制化实现不同App的功能提供了基础，App开发人员在使用Zygote开发时，只需要根据自身业务对该组件进行相应的
修改以满足不同的需求。
naming convention ：bus_+component name
constraints：可以依赖基础组件，通用组件，功能组件

* **App组件**,App组件属于Zygote框架的最上层，在这一层开发人员可以根据具体业务进行组件划分，在各个组件中依赖于业务组件及功能组件实现各
模块。
naming convention ：module_+component name
constraints：可以依赖于业务组件和功能组件

## Principle

## Use

## Contact

