# Switch scene
1. reSetScene(ActionEvent:actionEvent, String:fxmlName):void
   用于onAction的回调
```java
new Tools().reSetScene(e,"fxmlName");
```

2. reSetScene(String:fxmlName):void
   用于在只能用非静态方法的情况下调用
```java
new Tools().reSetScene("fxmlName");
```

3. changeScene(String:fxmlName):void
   静态方法调用，适用范围较广
```java
Tools.changeScene("fxmlName");
```

# Login verification
loginValidation(String:email, String:password):int
```java
int result = new Tools().loginValidation(email, password);
```
return code
```java
0: success
1: email not found
2: password not match
3: unknown error
```

# Encryption
encrypt(String:key, String:content):String
```java
String encrypted = Encryption.AESEncrypt(123456, "this is a secret");
```

decrypt(String:password):String
```java
Encryption.AESDecrypt(123456, ci05zm58UPwcaWMU9N6KjEk0SJHXTWnQmWHIIDF1EIQ=);
```

Demo
```java
String data = "This is not easy as you think";
String key = "12312312312123";
String encrypted = Encryption.AESEncrypt(key, data);
System.out.println(encrypted);
System.out.println(Encryption.AESDecrypt(key, encrypted));
```

# BrowerView
```java
String url = "http://127.0.0.1:8080/slideVerify/index.html";
BrowserModal browserModal = new BrowserModal(375, 450, url) ;
browserModal.setModality();
Function func = (Function<String,Void>) (message) -> {
if (Objects.equals(message, "Verification succeeded")) {
StatusContainer.ifVerify = true;
System.out.println(message);
Platform.runLater(() -> {
//code
try {
Thread.sleep(2 * 1000);
browserModal.stage.close();

} catch (InterruptedException ie) {
Thread.currentThread().interrupt();
}
});
}
return null;
};

browserModal.setFunction(func);
browserModal.show();
```

# MessageBox
```java
MessageFrame messageFrame = new MessageFrame(MessageFrameType.CONFIRM, "This is a notification message.This is a notification messageThis is a notification messageThis is a notification message");
messageFrame.setCallbackfunc((Integer i) -> {
System.out.println("callback called!!!!!!!!!!!!!!");
return null;
});
messageFrame.show();
```
一共有五种类型： CONFIRM, ERROR, INFO, SUCCESS, WARNING
构造器有两种：
一种是只有类型和message 的（标题为默认）
另一种是类型，message，标题，

## 如何让页面支持messagebox
```java
SignUpPageController extends Controller
```
```java

```java

@FXML
public Pane mainPane;


@FXML
private  void initialize(){
StatusContainer.currentPageController = this;
}
```
继承Controller类，然后公开最外面的pan之后，将当前page实例设置为当前pane的controller

## confirm messageBox demo
```java
MessageFrame messageFrame = new MessageFrame(MessageFrameType.CONFIRM, "Do you want to accept this order?");
messageFrame.setSuccessCallbackFunc((i) -> {
System.out.println(" accept order");
return null;
});

messageFrame.setFailedCallbackFunc((i) -> {
System.out.println(" reject order");
messageFrame.close();
return null;
});
messageFrame.show();
```

# Modal Winow
```java
String url = ConfigFile.backendPost +  "slideVerify/index.html";
BrowserModal browserModal = new BrowserModal(375, 450, url) ;
// modal
browserModal.setModality();
Function<String, Void> func =(message) -> {
if (Objects.equals(message, "Verification succeeded")) {
StatusContainer.ifVerify = true;
System.out.println(message);
Platform.runLater(() -> {
//code
try {
Thread.sleep(2 * 1000);
storeNewCustomerInfo();
browserModal.stage.close();
showSuccessMessage();

} catch (InterruptedException ie) {
Thread.currentThread().interrupt();
}
});
}
return null;
};
// callback base on console message
browserModal.setFunction(func);
browserModal.show();
```


carInfo - status
- 0: not exist
- 1: ready
- 2: in rent
- 3: reserved
- 4: repairing

order - status
- 0: not paid == in progress
- 1: paid
- -1: canceled == cancel
- 2: delivering
- 3: delivered == continuing
- 4: driving
- 5: finished == complete

# create webview
```java
WebView webView = new WebView();
WebEngine engine = webview.getEngine();
URL url = this.getClass().getResource("/com/example/car_rental_sys/html/radar.html");
// load html 
Platform.runLater(() -> {
   engine.setJavaScriptEnabled(true);
   engine.getLoadWorker().stateProperty().addListener(
   (ov, oldState, newState) -> {
   if (newState == Worker.State.SUCCEEDED) {
   engine.executeScript("initRadar("+radarDataStr+");");
}
});
   // execute js
   
assert url != null;
});
assert url != null;
engine.load(url.toString());
```

# create a jxBrowser windows

```java
Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);

Browser browser = engine.newBrowser();

//browser.navigation().loadUrl(new File("src/.../index.html").getAbsolutePath());
browser.navigation().loadUrl("https://html5test.com");

BrowserView view = BrowserView.newInstance(browser);

Scene scene = new Scene(new BorderPane(view), 1280, 800);
primaryStage.setTitle("JxBrowser JavaFX");
primaryStage.setScene(scene);
primaryStage.show();

primaryStage.setOnCloseRequest(event -> engine.close());

//execute js
Frame frame = browser.frames().get(0);
frame.executeJavaScript(jsArgs);

//dev
browser.devTools().show();

// get js console message
browser.on(ConsoleMessageReceived.class, event -> {
ConsoleMessage consoleMessage = event.consoleMessage();
String message = consoleMessage.message();
System.out.println(message);
});
```

# Message
status:
- 0: not read
- 1: read

type:
- 0: system(blue)
- 1: customer service(green)
- 2: user chat

system userID = 0
customer service userID = 0



# Tools

## DataToolos

> 关于路径，如无特别说明，指的都是项目路径，例如：src/main/java/com/example/car_rental_sys/ToolsLib/NetTools.java

- `public static String[] getRenterNameAndPostFromUserID(int userID)`


传入用用户id，返回用户的姓名和职位/vip等级

- `public static String[] getCarSeatsSpeedPowerFromCarModel(String carModel)`


传入车的型号，返回座位数，速度，马力

- `public static boolean ifFileExist(String filePath)`


传入文件的路径，判断是否存在

- `public static boolean ifDataFileExist(String fileName)`


传入data文件夹下的txt文件名，判断是否存在

- `public static boolean deleteFile(String filePath)`


传入文件的路径，删除文件

- `public static boolean deleteDataFile(String fileName)`


传入data文件夹下的txt文件名，删除对应文件

- `public static String[] getAllFileNameFromPath(String path)`


获得传入的文件夹下的文件名列表

- `public static String[] getAllFileNameFromPath()`


获得data文件夹下的文件名列表

- `public static int getID(String tableName)`


对于任何存在id 的txt文件都适用，返回当前最大id + 1

- `public static String getProjectPath()`


获得项目运行路径（对于一些必须使用绝对路径的操作，可以此函数组装绝对路径）

- `public static int getNewUserID()`


获得新用户的ID，例如txt最后的一条用户信息记录，id为100，则返回101

- `public static boolean checkPassword(String password)`


检查密码强度，是否包含大小写，数字，特殊符号，返回真值

- `public static boolean checkEmail(String email)`


检查Email格式是否符合规范，返回真值

- `public static boolean resetPassword(String email, String newPassword)`


传入Email和新密码，将重置密码，返回重置是否成功

- `public static String getCarModelFromCarID(int carID)`


传入car id 返回车的型号

- `public static String getCarNumberFromCarID(int carID)`


传入car id 返回车牌号

- `public static String getGradientColorFromCarID(int carID)`


传入car id 返回用于组合渐变色的颜色，格式为：”#000000,#FFFFFF“

- `public static boolean logLogin()`


记录当前登录者的登陆时间，ip，地理信息等

- `public static int getCustomerOrderNum(int customerID)`


获得顾客当前已经下过的订单的数量

- `public static String[] getCustomerBankCardsList(int customerID)`


获得顾客所有的银行卡号列表

- `public static int getRandomInt(int min, int max)`


获得某区间的随机整数

- `public static String getUserNameFromUserID(int userID)`


输入用户id，返回用户名

- `public static boolean generateMessageJSON(int userID)`


传入用户id，将生成用户的所有消息记录，用于message 页面显示

- `public static String generateRandomInvoiceNo(int OrderNumber)`


传入订单id，将生成发票id，传入相同的订单id，返回值一致

- `public static int  getCarUnitPriceFromCarID(int carID)`


传入车的id，返回车每小时的价格

* public static String getGenderFromUserID(int userID)

传入用户id，返回性别

## DateTools

- `public static String getFormatDateTime()`


获得当前时间的标准字符串： 2020-11-14 20:00:00

- `public static String getNow()`


getFormatDateTime()的缩写，推荐使用

- `public static Date stringToDateObje(String date)`


将标准时间字符串转化为时间对象

- `public static String dateToString(Date date, String format)`


将时间对象转化为时间字符串

- `public static String getDataTimeAfterAWeek()`


获得一周后的日期时间，用于记住登录

- `public static int getHourDiff(Date start, Date end)`


获得两个时间对象的时间差，返回小时数

## FXTools

- ` public static void changeScene(String fxmlName)`


传入fxml 名称，将在渲染到主stage

```Java
FXTools.changeScene("loginPage.fxml");
```

- ` public static void componentTest(String fxmlName, double width, double height)`


用于测试组件，将组件的fxml 名称，宽高传入，将创建一个新的stage来显示

- `public static double[] getCenterOfScreen()`


获取用户屏幕中心点坐标

- `public static void setStageShowCenterOfScreen(Stage stage)`


传入stage，此stage的位置将至于屏幕中心

- `private static void  showAStage(double width, double height, String fxmlName)`


用于测试组件，将组件的fxml 名称，宽高传入，将创建一个新的stage来显示

- ` public static double[] getMousePosition()`


获取用户当前鼠标的位置

- `public static void  printJavaFXNode(Node node)`


打印fx 节点为pdf

## ImageTools

- `public static Image getCircleImages(String fileUrl)`


传入图片地址，返回一个圆形的image对象

- `public static Image getCircleImages(Image image)`


传毒image对象，裁切后返回

- `public static Image getImageObjFromPath(String path)`


传入图片路径，返回图片对象

- `public static Image getUIImageObjFromName(String name)`


传入UI文件夹下的文件名，返回图片对象

- `public static Image getImageObjFromUserID(int userID)`


传入用户ID，返回用户头像的image对象

- `public static void setImageShape(ImageView imageView, double radius)`


传入一个ImagevView, 此ImagevView将会被裁剪为矩形圆角

- `public static void setImageShapeToCircle(ImageView imageView)`


传入一个ImagevView, 此ImagevView将会被裁剪为圆形

- `public static Image getBadgeImage(int UserID)`


传入用户ID，返回用户VIP徽章的image对象

- `public static Image getVIPCardImage(int UserID)`


传入用户ID，返回用户VIP卡片的image对象

- `public static Image getAvatarFromUserID(int userID)`


传入用户ID，返回用户头像的image对象

- `public static int[] getImageSize(String path)`


传入图片路径，返回图片的宽高

- `public static boolean covertImageToPng(String inputFile, String outputFile)`


将图片转化为png格式

- `public static void  generateQRCode(ImageView imageView, String content, int width, int height)`


将指定宽高的qrcode的内容渲染到imageView

- `public static void yAxisFlip(ImageView imageView, double a1, double a2)`


imageView左右镜像翻转



## NetTools

- `public static String getExternalHostIP()`


获得外网IP地址

- `public static String getLocalHostIP(String type)`


获得本地主机名称和IP

- `public static String getGeoIPInfo()`


获得IP地理信息

- `public static String sendRequest(String urlParam,String requestType)`


发送http请求

- `public static boolean netIsAvailable(String urlParam)`


检查网络连接情况

- `public static void StartHttpServer()`


启动后端服务器

- `public static void registerJxBrowserLicence()`


注册JxBrowser

## PlatformTools

- `public static void exebat(String strcmd)`


执行cmd 脚本程序

- `public static void startWindowNetworkSetting()`


启动Windows wifi 设置

- `public static void callWhatsApp(String phoneNumber)`


唤起WhatsApp

- `public static String getPropertyOsName()`


获得当前平台的型号

- `public static String getScreenShot()`


获得当前屏幕截图的路径

- `public static Image getScreenShotImageObj()`


获得当前屏幕截图的image对象

- `public static void openScreenShotWithMsPaint()`


使用paint 打开屏幕截图

## SelfTestTools

- `public static void executeSelfTest()`


执行所有的自检程序

- `private static void isNetworkConnected()`


检查网络情况

- `private static void isDataFileComplete()`


检查数据文件的完整性

- `private static void isBackendServerRunning()`


检查后端的运行状态

- `private static void isSecretFilesDecrypted()`


检查加密文件的解密状态

- `private static void isAllFilesDecrypted()`


检测是否全部解密

- `private static void isSecretFileExist()`


检测是否有加密文件存在

- `private static void isOriginalSecretFileExist()`


检测被加密文件是否存在

- `private static void isJxBrowserLicensed()`


检测JxBrowser注册状态

## StringTools

- `public static String capitalizeWord(String str)`


大写一个**句子**中的所有单词

- `public static String capitalizeFirstLetter(String str)`


大写**单词**首字母

- `public static String replaceSpacialChar(String str)`


删除字符串中所有的特殊符号： \n`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥（）【】‘；：”“’。， 、？

