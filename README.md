# Java_Car_Rental_System

<a name="readme-top"></a>


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/yuenci/Java-Car-Rental-System">
    <img src="https://github.com/yuenci/Java-Car-Rental-System/blob/master/src/main/resources/com/example/car_rental_sys/image/UI/logo_light_green.png" alt="Logo" height="80">
  </a>

  <h3 align="center">Car Rental System</h3>

  <p align="center">
    An awesome Car Rental System build with Java!
    <br />
    <a href="Doc/Tutorial.md"><strong>Explore the tutorial doc »</strong></a>
    <br />
    <br />
    <a href="Doc/Dev.md">Dev doc</a>
    ·
    <a href="https://github.com/yuenci/Java-Car-Rental-System/issues">Report Bug</a>
    ·
    <a href="https://github.com/yuenci/Java-Car-Rental-System/issues">Request Feature</a>
  </p>
</div>




<!-- ABOUT THE PROJECT -->
## About The Project

![product-screenshot](https://github.com/yuenci/Java-Car-Rental-System/blob/master/src/main/resources/com/example/car_rental_sys/image/others/mainPage.png)

Welcome to our rental car system!

This project was developed as part of the Object Oriented Development with Java module, using Java as the programming language and TXT files as the data storage. Our goal was to create a modern, user-friendly, and highly scalable Java FX project. To achieve this, we spent over 300 hours and two months of development to create a product that we are proud of.

To make our UI as visually appealing as possible, we used Figma for extensive design work ([check here](https://www.figma.com/file/RE7NHdlnO52kMQa0YttcZB/Top-Gun?node-id=0%3A1&t=we9XeUmdO0a0OczL-1)), resulting in a sleek and user-friendly interface. We hope you enjoy using our rental car system as much as we enjoyed creating it!

To ensure the scalability of our program, we implemented a modular design that separates the interface, events, tools, and business logic. We also abstracted and encapsulated various features to further improve our program's structure.

For the data layer, we used TXT files as our storage medium, which greatly improved the performance of our program. However, in order to be able to upgrade to a database storage solution in the future, we created a [SQL library](https://github.com/yuenci/sqlParser-SQL2TXT)  that allows us to use SQL to operate on TXT files. This not only made development more convenient for us, but it also means that when we do decide to migrate to a database, we can do so without having to rewrite our entire program.

In addition to these core features, we have also included a number of fun and modern touches, such as email verification, slider verification, internet connectivity detection, music playback, and a map component. We hope you enjoy using our rental car system as much as we enjoyed creating it!

During the development of this project, we received a lot of help and support from our friends, for which we are extremely grateful. We would also like to express our special thanks to the team at jxbrowser for extending our trial period twice to support our project.

We hope you enjoy using our rental car system. If you do, please consider giving us a star to show your support.

Thank you for support our project! 

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With


<a href="https://www.java.com">
<img src="https://github.com/yuenci/Java-Car-Rental-System/blob/master/src/main/resources/com/example/car_rental_sys/image/others/java_logo_icon.png" alt="JavaLogo" height="60">
</a>

<a href="https://openjfx.io/">
<img src="https://github.com/yuenci/Java-Car-Rental-System/blob/master/src/main/resources/com/example/car_rental_sys/image/others/JavaFX_Logo.png" alt="JavaFXLogo" height="60"> 
</a>

<a href="https://nodejs.org/">
<img src="https://github.com/yuenci/Java-Car-Rental-System/blob/master/src/main/resources/com/example/car_rental_sys/image/others/Node.js_logo.png" alt="JavaFXLogo" height="60"> 
</a>

<a href="https://jxbrowser-support.teamdev.com/">
<img src="https://github.com/yuenci/Java-Car-Rental-System/blob/master/src/main/resources/com/example/car_rental_sys/image/others/jxBrower.png" alt="jxBrowerLogo" height="60"> 
</a>

<a href="https://developers.google.com/maps">
<img src="src/main/resources/com/example/car_rental_sys/image/others/google-maps-platform-1200x675%201.png" alt="GoogleMapLogo" height="60"> 
</a>

<a href="https://github.com/yuenci/sqlParser-SQL2TXT">
<img src="https://github.com/yuenci/sqlParser-SQL2TXT/blob/master/src/example/SQLTOPNG.jpg" alt="JavaLogo" height="60">
</a>

* [JSqlParser](https://github.com/JSQLParser/JSqlParser) - SQL statement parser
* [SQL2TXT](https://github.com/yuenci/sqlParser-SQL2TXT) - SQL statement operation txt file CRUD
* [Google Map platform](https://developers.google.com/maps/documentation/embed/get-started) - Map embed frame 
* [Google Maps JavaScript API](https://developers.google.com/maps/documentation/javascript/examples) - Map component 
* [JxBrowser](https://www.teamdev.com/jxbrowser) - a Chromium web browser for javaFX
* [Apache Commons Codec](https://mvnrepository.com/artifact/commons-codec/commons-codec) - Md5 encryption
* [vue-monoplasty-slide-verify](https://github.com/monoplasty/vue-monoplasty-slide-verify) - slide verify 
* [IP API](https://ip-api.com/) - IP Geolocation API

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started
Welcome to our project! Before you can get started, you will need to make sure that you have a device that is capable of running Java. If you do not already have Java installed on your device, you can download it from the official Java website.

In addition to having Java installed, you will also need to obtain any necessary API or license keys for the third-party services that our project relies on. These keys will likely be required in order to access certain features or functionality within the project. If you are unsure of which keys you need or how to obtain them, please consult the documentation for the specific service or reach out to the service provider for assistance.

Once you have Java installed and have obtained the necessary keys, you are ready to launch the project. Follow the instructions in the documentation to get up and running. We hope you enjoy using our project!

### Prerequisites
1. You need to install the Java environment first, download Java JDK18 here: https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html.
2. Get your SMTP email address, password and SMTP server address.
3. Get JxBrowser license key here: https://www.teamdev.com/jxbrowser#evaluate
4. Get Google Map API key here: https://developers.google.com/maps/documentation/javascript/get-api-key
5. Get remove.bg API key here: https://www.remove.bg/zh/tools-api

After you get all the keys, you need to modify the following files: src/main/java/com/example/car_rental_sys/ConfigFile.java
```java
// Email service
public static final String myEmailAccount = "email address";
public static final String myEmailPassword = "email password";
public static final String myEmailSMTPHost = "email SMTP server address";

// jxBrowserLicense key
public static final String jxBrowserLicense = "your jxBrowserLicense key";

// Google Map API key
public static final String googleMapAPIKey = "your google map API key";

// remove.bg API key
public static final String removebgKey = "your remove.bg API key";
```


### Installation

1. Make sure you have Git installed on your device. If you don't have it, you can download it from the official Git website (https://git-scm.com/).

2. Open a terminal or command prompt and navigate to the directory where you want to clone the repository.

3. Use the git clone command to clone the repository. The repository URL can be found on the GitHub page for the project. 
```shell
git clone https://github.com/yuenci/Java-Car-Rental-System
```
4. Open the project in your IDE and run the Application.main method in the Application.java file.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- UI design -->
## UI design
### Customer UI
<details><summary>Main Page</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/main.png"  height="300"> 
</details>

<details><summary>About Us</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/aboutUs.png"  height="300"> 
</details>

<details><summary>Contact Us</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/contactUs.png"  height="300"> 
</details>

<details><summary>Register</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/register.png"  height="300"> 
</details>

<details><summary>View Car</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/viewCars.png"  height="300"> 
</details>

<details><summary>Car Details</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/carDetails.png"  height="300"> 
</details>

<details><summary>Comments</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/comments.png"  height="300"> 
</details>

<details><summary>Payment</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/payment.png"  height="300"> 
</details>

<details><summary>Place Order Animation</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/waiting.png"  height="300"> 
</details>

<details><summary>Order Placed</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/paid.png"  height="300"> 
</details>

<details><summary>Wallet</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/wallet.png"  height="300"> 
</details>

<details><summary>Add New Payment Card</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/addCards.png"  height="300"> 
</details>

<details><summary>Order History</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/history.png"  height="300"> 
</details>

<details><summary>Track Order</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/trackOrder.png"  height="300"> 
</details>

<details><summary>Invoice</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/invoice.png"  height="300"> 
</details>

<details><summary>Giving Feedback</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/feedback.png"  height="300"> 
</details>

<details><summary>Settings</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/setting.png"  height="300"> 
</details>

### Admin UI
<details><summary>Dashboard</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/dashboard.png"  height="300"> 
</details>

<details><summary>Analysis</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/analysis.png"  height="300"> 
</details>

<details><summary>Message</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/message.png"  height="300"> 
</details>

<details><summary>Manage Vehicle</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/manageVehicle.png"  height="300"> 
</details>

<details><summary>Add New Vehicle</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/addVehicle.png"  height="300"> 
</details>

<details><summary>Edit Car's Details</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/editVehicle.png"  height="300"> 
</details>

<details><summary>CLICK ME</summary>

</details>

### Driver UI
<details><summary>Workplace</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/driverService.png"  height="300"> 
</details>

<details><summary>Driving Mode</summary>
<img src="src/main/resources/com/example/car_rental_sys/image/others/UI_design_screenshot/driverMode.png"  height="300"> 
</details>

<details><summary>CLICK ME</summary>

</details>



<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap
- [ ] Change the database from TXT to MySQL
- [ ] Add more features to the system



See the [open issues](https://github.com/yuenci/Java-Car-Rental-System/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Authors
<a href="https://github.com/yuenci">
  <img src="https://github.com/yuenci/Laptop-Repair-Services-Management-System/blob/master/image/avatar-innis.png" alt="profile image" width="60px">
</a>

<a href="https://github.com/Kaikiat1126">
  <img src="https://github.com/yuenci/Java-Car-Rental-System/blob/master/src/main/resources/com/example/car_rental_sys/image/others/kk-Profile-logo.png" alt="profile image" width="61px">
</a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

Innis - [yuenci1575270674@gmail.com](yuenci1575270674@gmail.com) - https://www.enjoycoding.me/

Kai Kiat - https://github.com/Kaikiat1126

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the GPL License. See [LICENSE](./LICENSE) for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>







<!-- ACKNOWLEDGMENTS -->
## Acknowledgments
* [JxBrowser](https://jxbrowser-support.teamdev.com/docs/guides/content.html)
* [Web Design: Rent - Car Rental Home Page](https://dribbble.com/shots/17734452-Web-Design-Rent-Car-Rental-Home-Page)
* [Electric Car Dashboard](https://dribbble.com/shots/19111426-Electric-Car-Dashboard)
* [Mangrove: Settings Interaction](https://dribbble.com/shots/17698340-Mangrove-Settings-Interaction)


<p align="right">(<a href="#readme-top">back to top</a>)</p>

