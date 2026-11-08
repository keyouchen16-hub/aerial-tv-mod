# Aerial · 个人魔改版

基于 [pengchujin/aerial-tv](https://github.com/pengchujin/aerial-tv) v1.87 的私人修改版，保留原 MIT 许可与署名。

**这不是官方版本**，修改与构建由本人完成；原项目版权归 pengchujin 所有。

## 改了什么

### 时钟定制
- 5 种时钟字体：Inter、Space Grotesk（几何）、JetBrains Mono（等宽）、Sora、Oxanium（科幻）。
- 合并「时钟大小/字号」为单一「时钟样式」：角落紧凑 / 角落标准 / 角落大 / 屏幕大钟。
- 3 种颜色：白 / 银 / 冰蓝。
- 入口：控制中心 → 个性化 → 时钟字体 / 时钟样式 / 时钟颜色。

### 新壁纸
- 轨道 · 夜（星空 + 地球弧线）
- 星云 · 深空
- 极光 · 山脊
- 网格 · 赛博
- 每日 · 深空（按日期变化的程序化星云）

### 其他
- 恢复玻璃底板与天气显示为默认。
- 手机配置接口新增 `POST /api/clock`（face / size / tint / layout）。

## 构建

需要 JDK 17 与 Android SDK 36：

```bash
export JAVA_HOME=/path/to/jdk17
export ANDROID_HOME=/path/to/android-sdk
./gradlew assembleStandardRelease
```

产物：`app/build/outputs/apk/standard/release/app-standard-release.apk`。

默认使用本机 debug 签名；要与本仓库 Release 的 APK 互相覆盖，请使用同一签名密钥。

## 许可

代码沿用上游 MIT（见 `LICENSE`）。新增字体为 SIL Open Font License 1.1，见 `FONT-LICENSE-OFL.txt`。
