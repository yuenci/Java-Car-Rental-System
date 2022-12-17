# Tools
# Data Tools
- `getRenterNameAndPostFromUserID(int userID)  : String[]`

- `getCarSeatsSpeedPowerFromCarModel(String carModel)  : String[]`

- `ifFileExist(String filePath)  : boolean`

- `ifDataFileExist(String fileName)  : boolean`

- `deleteFile(String filePath)  : boolean`

- `deleteDataFile(String fileName)  : boolean`

- `getAllFileNameFromPath(String path)  : String[]`

- `getAllFileNameFromPath()  : String[]`

- `getID(String tableName)  : int`

- `getProjectPath()  : String`

- `String getAbsolutePath(String relativePath)  : `

- `getNewUserID()  : int`

- `getNewID(String tableName)  : int`

- `getRandomGender() : String`

- `checkPassword(String password)  : boolean`

- `checkEmail(String email)  : boolean`

- `checkPhoneNumber(String phoneNumber)  : boolean`

- `resetPassword(String email, String newPassword)  : boolean`

- `getCarModelFromCarID(int carID)  : String`

- `getModelIDFromCarModel(String carModel)  : int`

- `getCarNumberFromCarID(int carID)  : String`

- `getGradientColorFromCarID(int carID)  : String`

- `encryptDataFiles()  : boolean`

- `decryptDataFiles()  : boolean`

- `logLogin(Boolean ifRememberMe)  : boolean`

- `getCustomerOrderNum(int customerID)  : int`

- `gerCustomerLevel(int customerID) : int`

- `getTotalOrderNum()  : int`

- `getTotalTransactionNum(int customerID) : int`

- `getTotalSpending(int customerID) : String`

- `getOrderIDStr(int orderID)  : String`

- `getOrderStatusWithID(int orderID) : int`

- `updateOrderStatusWithID(int orderID, int status) : void`

- `getCustomerBankCardsList(int customerID)  : String[]`

- `getCustomerBankCardsData(int customerID)  : ArrayList<String[]>`

- `getRandomInt(int min, int max)  : int`

- `getUserNameFromUserID(int userID)  : String`

- `getUserRoleFromUserEmail(String userEmail)  : String`

- `getUserRoleFromUserID(int userID)  : String`

- `getEmailFromUserID(int userID)  : String`

- `generateMessageJSON(int userID)  : boolean`

- `generateRandomInvoiceNo(int OrderNumber)  : String`

- `getCarUnitPriceFromCarID(int carID)  : int`

- `getCarUnitPriceFromCarModel(String carModel)  : int`

- `getGenderFromUserID(int userID)  : String`

- `ifCurrentCustomerHasDLNumber()  : boolean`

- `keepUserLoggedIn()  : boolean`

- `logOut()  : boolean`

- `setOrderStatus(int orderID, int status)  : boolean`

- `setOrderStatus(int orderID, int status, String relate)  : boolean`

- `updateCarStatus(int carID, int status)  : boolean`

- `getStatusMessage(int orderID, int status)  : String`

- `getAdminToDo()  : ArrayList<String[]>`

- `setTaskAsDone(int todoID)  : boolean`

- `addTask(String content, String due)  : boolean`

- `fileChooser()  : String`

- `copyFileUsingApacheCommonsIO(String sourceAbsolutePath, String destAbsolutePath) throws IOException  : void`

- `getOrderInfoFromOrderID(String orderID)  : String[]`

- `getCarStar()  : double`

- `getCarSpeed()  : int`

- `getCarPower()  : double`

- `getRandomCarStatus()  : int`

- `getAvailableCars()  : ArrayList<Integer>`

- `ifCarsAvailable(String carModel)  : boolean`

- `carsAvailable(String carModel)  : ArrayList<Integer>`

- `carsAvailable(int carModelID)  : ArrayList<Integer>`

- `getTableData(ArrayList<String[]> data, int page, int max)  : ArrayList<String[]>`

- `getTax() : String`

- `getDiscount() : double`

- ` getDiscountNum(int unitPrice, int hours) : int`

- `getBalance() : int`

- `getPayPalAccountFromUserID(int userID) : String`

- `getPhoneFromUserID(int userID) : String`

- `getCarIDFromOrderID(int carID) : int`

- ` storeCommentToDB(int commentID, String type, String payload, int posterID, String commentStr, int relevantCommentID, : boolean`

- `storeComment(int rating , String comment, int orderID) : boolean`

- `boolean sendSystemMessage(int userID, String message) : `

- `generateDashboardData() : boolean`

- `generateAnalysisData() : boolean`

- `generateWalletRatioData(String carData) : boolean`

- `generateWalletLineData() : boolean`

## Date Tools
- `getFormatDateTime()  : String`

- `getNow()  : String`

- `stringToDateObje(String date)  : Date`

- `dateToString(Date date, String format)  : String`

- `dateToString(String date, String format)  : String`

- `getDataTimeAfterAWeek() : String`

- `getDataTimeAfterDays(int days) : String`

- `startTime ; : long`

- `setStarTime() : void`

- `getTimeCost() : String`

- `getHourDiff(Date start, Date end) : int`

- `getHourDiff(String startStr, String endStr) : int`

- `dateTimeToTimestamp(String time)  : long`

- `timeStampToDateTime(long timestamp)  : String`

- `timeStampToDateTime(String timestamp)  : String`

- `getHourDiff(long start, long end) : int`

- `validateDate(String startTime, String endTime)  : boolean`

- `getSpecFormatDateTime(Date date)  : String`

- `getDateWithSlash(Date date)  : String`

- `getSpecFormatDate(String dateStr) : String`

## FX Tools
- `changeScene(String fxmlName)  : void`

- `initFXML(Pane pane, String fxml)throws IOException  : void`

- `componentTest(String fxmlName, double width, double height) throws IOException  : void`

- `getCenterOfScreen()  : double[]`

- `setStageShowCenterOfScreen(Stage stage)  : void`

- `startLoadingStage() throws IOException  : void`

- `showNetworkErrorPage() throws IOException  : void`

- `showErrorsPage() throws IOException  : void`

- `ifShowDiagnosticDataPageOpen = false; : boolean`

- `showDiagnosticDataPage() throws IOException  : void`

- `checkCurrentUserStage() : boolean`

- `goToSettingPage() : void`

- `getMousePosition()  : double[]`

- ` printJavaFXNode(Node node) : void`

- `getStageFromFXNode(Node node) : Stage`

- `getMapComponent() : Pane`

- `getNodeBgColor(Node node) : String`

- `validInputLength(TextField textField, String type, String oldValue, String newValue) : void`

- `validInputNumber(TextField textField, String type, String oldValue, String newValue) : void`

- `validInputIsDate(TextField textField, String regex, String newValue)  : void`

- `validInputIsEmail(TextField textField, String newValue) : boolean`

- `validInputIsPhone(TextField textField, String newValue) : boolean`

- `validInputCardValid(TextField textField, String newValue) : boolean`

- `validInputCardNumber(TextField textField, String newValue) : boolean`

- `validInputCardValueLength(TextField textField, String newValue) : boolean`

- `validInputBillingAddress(TextArea textArea, String oldValue, String newValue) : boolean`

- `pandaHead() : void`

- `dogHead() : void`

## String Tools
- `capitalizeWord(String str)  : String`

- `capitalizeFirstLetter(String str)  : String`

- `replaceSpacialChar(String str)  : String`

- `ifStringContainsNumberAndSpecialCharacter(String str)  : boolean`


## Image Tools
- `getCircleImages(String fileUrl)  : Image`

- `getCircleImages(Image image)  : Image`

- `getImageObjFromPath(String path)  : Image`

- `getUIImageObjFromName(String name)  : Image`

- `getImageObjFromUserID(int userID)  : Image`

- `setImageShape(ImageView imageView, double radius)  : void`

- `setImageShapeToCircle(ImageView imageView)  : void`

- `getBadgeImage(int UserID)  : Image`

- `getVIPCardImage(int UserID)  : Image`

- `getUserAvatarFromUserID(int userID)  : Image`

- `getImageSize(String path)  : int[]`

- `covertImageToPng(String inputFile, String outputFile)  : boolean`

- `getNewAvatar(String inputFile, int userID) : Image`

- ` generateQRCode(ImageView imageView, String content, int width, int height)  : void`

- `yAxisFlip(ImageView imageView, double a1, double a2)  : void`

- `getColorSetsFromImage(String imagePath) : ArrayList<int[]>`

- `getCarImageObjFromCarID(int carID) : Image`

- `removeBackground(String imagePath) : Image`

- `renameCarImage(String imagePath,String newCarName) : void`

- `centerImage(ImageView imageView) : void`

- `deleteFile(String imgPath) : void`

- `getDefaultProfile(String gender) : Image`

## Network Tools    
- `getExternalHostIP()  : String`

- `getLocalHostIP(String type) : String`

- `getGeoIPInfo() : String`

- `sendRequest(String urlParam,String requestType)  : String`

- `netErrorMsg = ""; : String`

- `netIsAvailable(String urlParam)  : boolean`

- `StartHttpServer()  : void`

- `registerJxBrowserLicence() : void`

## Platform Tools
- `exebat(String strcmd)  : void`

- `startWindowNetworkSetting()  : void`

- `callWhatsApp(String phoneNumber)  : void`

- `getPropertyOsName()  : String`

- `getScreenShot() : String`

- `getScreenShotImageObj() : Image`

- `openScreenShotWithMsPaint() : void`

## Self-test Tools
- `executeSelfTest()  : void`


# Customized JavaFX Components
## How to switch fx scene
use `FXTools.changeScene(String fxmlName)` to switch scene

## How to use login function
```java
int result = new Tools().loginValidation(email, password);

// return code 
// 0: success
// 1: email not found
// 2: password not match
// 3: unknown error
```

## How to encrypt and decrypt content
```java
// encrypt
String encrypted = Encryption.AESEncrypt(123456, "this is a secret");

// decrypt
Encryption.AESDecrypt(123456, ci05zm58UPwcaWMU9N6KjEk0SJHXTWnQmWHIIDF1EIQ=);


String data = "This is not easy as you think";
String key = "12312312312123";
String encrypted = Encryption.AESEncrypt(key, data);
System.out.println(encrypted);
System.out.println(Encryption.AESDecrypt(key, encrypted));
```


## How to use Notification box
```java
MessageFrame messageFrame = new MessageFrame(MessageFrameType.CONFIRM, "This is a notification message.This is a notification messageThis is a notification messageThis is a notification message");
messageFrame.setCallbackfunc((Integer i) -> {
System.out.println("callback called!!!!!!!!!!!!!!");
return null;
});
messageFrame.show();
```

There are five types: confirm, error, info, success and warning. 
There are two kinds of constructors: one with only type and message (the title is the default) and the other with type, message and title.

Before you show the notification box, you should set FXML controller's, let controller extends `Controller` and 
set 

```java

@FXML
public Pane mainPane;


@FXML
private  void initialize(){
StatusContainer.currentPageController = this;
}

```
**WARNING messageBox demo**
```java
MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "this is message");
messageFrame.show();
```

**Confirm messageBox demo**
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

## How to use JavaFX webview
```java
ebView webView = new WebView();
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

## How to use JXBrowser
```java
System.setProperty("jxbrowser.license.key", ConfigFile.jxBrowserLicense);
// get jxBrowser license

Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);

// HARDWARE_ACCELERATED:  use GPU to accelerate rendering
// RenderingMode.OFF_SCREEN
        

Browser browser = engine.newBrowser();

//browser.navigation().loadUrl(new File("src/.../index.html").getAbsolutePath())
// load local html file
        
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

//use dev tool
browser.devTools().show();

// get js console message
browser.on(ConsoleMessageReceived.class, event -> {
    ConsoleMessage consoleMessage = event.consoleMessage();
    String message = consoleMessage.message();
    System.out.println(message);
});
```

