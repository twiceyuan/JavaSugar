# JavaSugar

[![](https://jitpack.io/v/twiceyuan/JavaSugar.svg)](https://jitpack.io/#twiceyuan/JavaSugar)

一个 Java 类解决一个场景问题

### 1. TaskValve

这个工具取名为了 TaskValve，是任务阀门的意思。主要用于简化许多任务的完成必须依靠另一个异步任务完成为前提的场景。

例如 Android 6.0 中的动态权限处理：

假如一个界面有三处需要定位权限，这三个任务在用户点击对应的操作都触发了，这时它们可以先加入到这个任务队列中，等待用户点击允许（开启阀门），这时所有队列中的任务都会被释放并执行一遍；

如果用户已经允许了，阀门开着的情况下任务就会直接执行。在实际使用时，开发者就不需要考虑用户是否允许了权限，而只需要把允许之后的任务放到队列中。用户默认下打开了阀门，那么任务将直接执行；用户没有打开阀门，任务将在打开后挨个执行；用户拒绝了权限，清空队列。

### 2. IgnoredNull

IgnoredNull 顾名思义，忽略掉 null。大家知道 Java 直接忽略掉 null 后果很严重，手动判断又太麻烦。例如我现在有这么一个电脑实体类，它从服务端的接口获得，由 gson 装载数据到实体，我现在需要获得这个电脑的显示器的参数信息的水平像素个数，代码假设是 computer.getScreen().getInfo().getWidth()，但是我们服务端数据不全，有的电脑没有显示器信息，有的显示器又没有参数信息，有的参数信息里没有有水平分辨率像素个数。所以我们的代码可能就是：

```java
if (computer != null) {
   if (computer.getScreen() != null) {
      if (computer.getScreen().getInfo() != null) {
          if (computer.getScreen().getInfo().getWidth() != null) {
             int screenWidth = computer.getScreen().getInfo().getWidth(); // I get it!
          }
      }
   }
}
```

所以大多数情况我这样做

```java
int computerScreenWidth;
try {
   computerScreenWidth = computer.getScreen().getInfo().getWidth();
   // I get it!
} catch (NullPointException ignored) {
}
```

似乎省事了不少，但是 try catch 看起来很挫，写起来手感不好，看起来也不够直观。所以准备把它包装一下：

```java
IgnoredNull.of(() -> computer.getScreen().getInfo().getWidth()).ifPresent(width -> {
  // I get it!
});
```

只有这样吗？如果要考虑到为空的情况也要处理（大多数情况就是这样），那么第一种方法还要再加将近一倍的数量，或者增加一个方法；而第二种就是在 catch 里处理一次，非 catch 取到之后判断非空再一次。这样封装之后，在很多情况下我们不用打乱代码的层级结构就能取到深层次的对象或者值，就像 kotlin 或者 swift 中的 ? 操作符一样。

### 3. CheckNull

原理类似 IgnoredNull，也是避免复杂的多变量判空，回调一个 chekcer 使用其可变长度参数方法一次性传入所有需要判空、catch 空引用的变量统一处理其结果。

### 4. Tug

拔河任务模型。假设有多个任务 A（A1，A2，A3……），且所有任务 A 的完成时间先后不确定，但当所有 A 任务所有执行完毕后需马上执行任务 B。这种异步模型可以用 Tug 类简单解决：

```java
// 创建异步模型管理工具
Tug tetris = Tug.create();
tetris.addMember(member -> {
   // 执行耗时操作
   // ...
   member.ready(); // 通知耗时操作完成
}
// 添加其他任务
// tetris.addMember...

// 所有任务添加完毕
tetris.setAllReadyListener(() -> {
  // 所有任务完成后的操作
});
tetris.start(); // 开始监听
```
