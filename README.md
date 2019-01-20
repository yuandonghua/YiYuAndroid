# YiYuAndroid
艺语Android端
### 打包
1. GlobalConfiguration类加上@Keep注解，打包在主包内
### 夜间模式和日间模式切换注意事项
1. 根布局加上此属性tools:ignore="MissingPrefix"
2. 夜间模式颜色名字以night_开头，例如：<color name="night_bg">#3A3743</color>
3. 日间模式颜色名字以light_开头，例如：<color name="light_bg">#E3F2FD</color>
4. 夜间模式支持的属性: 
    ```
    View 级:
    nightBackground
    TextView 级:
    nightTextColor
    nightTextColorHighlight
    nightTextAppearance
    nightTextColorLink
    nightTextColorHint
    ListView 级:
    nightLVDivider
    LinearLayout 级别:
    nightDivider
    第三方控件支持: 
    nightBackground
    nightTextColor
    ```


