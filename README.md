# ScreenAdapter
一行代码适配所有Android屏幕
### 使用
[引入包](https://jitpack.io/#ReshapeDream/ScreenAdapter)
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
![](https://upload-images.jianshu.io/upload_images/11008950-c10d4bc064ed6dee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
