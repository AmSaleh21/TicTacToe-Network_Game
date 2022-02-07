# TicTacToe-Network_Game
Network based online Tic tac Toe game built with java

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#Team-Members">Team Members</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

<!-- [![Product Name Screen Shot][product-screenshot]](https://example.com) -->

This project consists of two Parts:-

1- Client side:

 -First, User Can SignUp With new Account, and then Can Login easily to the server. 

 -Allow the player to play in single mode( i.e. play with the Computer with three difficulties(easy ,medium ,hard).
 
 -Allow two players either to play locally on the same machine or  ??(on two different machines)??.
      
      • For the online mode:
      
      The application displays a list of the online or available users. So, the player can choose any of them to send him a request to play.
      
      • The other player receives a pop Up invition to play and he should have the decision to accept or refuse, 
        while he accepts the game his status changed to busy,
        and then he removed from player list and no one can send him request for another game during original game.
  
  
 -Online games are recorded by default on the database where all players can view the game after it has been finished

 -During Game, Players can chat with each other.
 
 -After Game, the application stores the Winner player score.
 
 -The application hava an elegantand easy user interface.

  
2- Server side:
   
   -The Server application handles the connections, streams and exchanging the data among the users.

   -List online players Connected to the network, List offline players Disconnected.
   
   -The server application has simple GUI.
   
   -Start / Stop Button to Activate or disable the server service. 
   
   -Pie chart graphs that show the number of active users or online and offline users.

<p align="right">(<a href="#top">back to top</a>)</p>

### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

* [Java](https://java.com/)
* [JavaFX](https://www.oracle.com/java/technologies/javase/javafx-overview.html)
* [MySQL](https://dev.mysql.com/)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

steps to setting up the project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

project was built under [NetBeans 8.2](https://netbeans-ide.informer.com/8.2/), though any ide can be used
MySql or xamp for the database

### Installation

  installing and setting up.

1. Clone project
2. Create local branch name **master**
   1. command  ```git branch master ```
3. Checkout to the master branch
   1. command ```git checkout master```
4. Lat step clone the Development branch to your local Development branch
   1. command ```git pull origin master```

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Team Members -->
## Team Members

1. Abdelrahman Mohamed Saleh.
2. Abdelrahman Mohamed Ali.
3. Ramez Yousef.
4. Nagwa Talaat.
5. Monica Ashraf.

<p align="right">(<a href="#top">back to top</a>)</p>