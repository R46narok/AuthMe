
<div id="top"></div>

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h1 align="center">AuthMe</h1>

  <p align="center">
    A solution for digital identity on the Internet
    <br />
    <a href="https://github.com/R46narok/AuthMe/tree/main/docs"><strong>Explore the docs »</strong></a>
  </p>
</div>



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
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

*AuthMe* gives people an opportunity to identify themselves to modern applications and in the same time protects their rights and privacy. 

The web application features:
* Authentic digital identity
* A convenient system for digital documents
* A way to fight cyberbullying, online crimes, etc.

The solutions follows the microservice approach and is designed to be scalable and able to withstand lots of requests per second. The architecture is security oriented.

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

The following frameworks/technologies were used to build the project:

* [Spring](https://spring.io/)
* [Microsoft Azure](https://azure.microsoft.com/en-us/)
* [ASP.NET Core](https://dotnet.microsoft.com/en-us/apps/aspnet)
* [Kubernetes](https://kubernetes.io/)
* [Docker](https://www.docker.com/)
* [Terraform](https://www.terraform.io/)
<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

Make sure you have the following packages/SDKs installed:
* Java - OpenJDK 17
* .NET Core 6
* Docker and docker-compose
* A valid Azure subscription

### Installation

_Below is an example of how to run the microservices and start the database services using docker-compose and the officially published images on [DockerHub](https://hub.docker.com/u/d3ds3g). _

1. Clone the repo
   ```sh
   git clone https://github.com/r46narok/AuthMe.git
   ```
2. Navigate into the root directory 
   ```sh
   cd AuthMe
   ```
3. Start the images in *detach mode*
   ```sh
   docker compose up -d # Windows
   ```
   ```sh
   docker-compose up -d # Linux
   ```

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
## Usage

To use the web application start a browser and go to http://localhost:8080. There you will interact with the front portal, written in Java Spring.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- ROADMAP -->
## Roadmap

- [x] Update README
- [ ] Document the project further

See the [open issues](https://github.com/r46narok/authme/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Authors

👤  **Dimitar Vasilev and Stanimir Kolev**

-   Github: [@BaiMitio](https://github.com/BaiMitio)  and  [R46narok](https://github.com/R46narok)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

The following libraries were used in the development of the project:

* [MediatR](https://github.com/jbogard/MediatR) - .NET library for CQRS and Mediator pattern
* [FluentValidation](https://fluentvalidation.net/) - .NET library for building strongly-typed validation rules
* [AutoMapper](https://automapper.org/) - .NET convention-based object-object mapper
* [Spring Security](https://spring.io/projects/spring-security) - Spring authentication and access-control framework
* [Thymeleaf](https://www.thymeleaf.org/) - Spring template engine

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/r46narok/authme.svg?style=for-the-badge
[contributors-url]: https://github.com/r46narok/authme/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/r46narok/authme.svg?style=for-the-badge
[forks-url]: https://github.com/r46narok/authme/network/members
[stars-shield]: https://img.shields.io/github/stars/r46narok/authme.svg?style=for-the-badge
[stars-url]: https://github.com/r46narok/authme/stargazers
[issues-shield]: https://img.shields.io/github/issues/r46narok/authme.svg?style=for-the-badge
[issues-url]: https://github.com/r46narok/authme/issues
[license-shield]: https://img.shields.io/github/license/r46narok/authme.svg?style=for-the-badge
[license-url]: https://github.com/r46narok/authme/blob/master/LICENSE.txt
[product-screenshot]: images/architecture.png