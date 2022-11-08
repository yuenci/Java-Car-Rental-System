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
- 0: not paid
- 1: paid
- 2: canceled
- 3: delivering
- 4: driving
- 4: finished