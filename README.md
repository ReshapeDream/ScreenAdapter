# ScreenAdapter
一行代码适配所有Android屏幕
### 使用
- 1. [引入包](https://jitpack.io/#ReshapeDream/ScreenAdapter)
- 2.代码调用
```
 setContentView(ScreenAdapter.inflater(this,R.layout.activity_main,false));
```

### 效果展示
布局文件代码
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:aimScreenHeight="1920px"
    app:aimScreenWidth="1080px"
    app:aimScalePixel="2.625">

    <View
        android:id="@+id/view1"
        android:layout_width="1060px"
        android:layout_height="80px"
        android:layout_gravity="center_horizontal"
        android:layout_marginLeft="10px"
        android:layout_marginRight="10px"
        android:layout_marginTop="20px"
        android:background="#f00" />

    <View
        android:onClick="click1"
        android:id="@+id/view2"
        android:layout_width="680px"
        android:layout_height="300px"
        android:layout_below="@id/view1"
        android:layout_marginLeft="200px"
        android:layout_marginRight="200px"
        android:layout_marginTop="20px"
        android:background="#f00" />

    <TextView
        android:layout_width="920px"
        android:layout_height="200px"
        android:layout_below="@id/view2"
        android:text="我是字体测试"
        android:textColor="#000"
        android:textSize="50sp" />
</RelativeLayout>
```
- 未适配

![未适配480x800](https://github.com/ReshapeDream/ScreenAdapter/blob/master/%E6%9C%AA%E9%80%82%E9%85%8D480x800.png)
- 适配各种屏幕

![适配各种屏幕](https://github.com/ReshapeDream/ScreenAdapter/blob/master/%E9%80%82%E9%85%8D%E5%90%84%E7%A7%8D%E5%B1%8F%E5%B9%95.jpg)
### 关于是否适配横竖切换 adapteScreenSwitch
- if false 

![false](https://github.com/ReshapeDream/ScreenAdapter/blob/master/%E6%A8%AA%E5%B1%8Ffalse.png)
- if true

![true](https://github.com/ReshapeDream/ScreenAdapter/blob/master/%E6%A8%AA%E5%B1%8Ftrue.png)
