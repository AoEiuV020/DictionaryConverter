#百度手机输入法中文词库转谷歌手机输入法字典
自用小工具，  
也许以后用其他输入法时还会更新，  

百度手机输入法的词库格式中的词频有点不清楚，  
暂且给它扣掉整数60000或55000再把0改成1,  

百度的词库放在res/ch3.txt,这个文件名是百度默认的，  
输出的谷歌字典在res/google.txt,

```sh
./gradlew run
```
