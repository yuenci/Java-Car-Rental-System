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

Our project is a car rental system with Java as the main back-end language and JavaFX as the interface design framework with various special and rich visual designs.

This project is a recent Java assignment for our team. In addition to meeting the assignment requirements, we also added more interesting features, most notably we embedded JXBrowser in the UI section to display more advanced screens



<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

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

Kai Kiat - [] - https://github.com/Kaikiat1126

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the GPL License. See [LICENSE](./LICENSE) for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>







<!-- ACKNOWLEDGMENTS -->
## Acknowledgments



<p align="right">(<a href="#readme-top">back to top</a>)</p>

