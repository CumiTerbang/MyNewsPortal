# MyNewsPortal Android App

<img src="https://github.com/CumiTerbang/MyNewsPortal/blob/master/readme_assets/screenshot_1.jpg" width="200" height="355,56"> <img src="https://github.com/CumiTerbang/MyNewsPortal/blob/master/readme_assets/screenshot_2.jpg" width="200" height="355,56">

an android app demo to browse and read international news articles using API from  [newsapi.org](https://newsapi.org/)
this is my approach for Bank Mandiri Technical Test provided by Nityo Infotech by making an android application based on the requirement as the solution

Click [here](https://drive.google.com/file/d/1hfTtxqt7IB4BPQDB1EKHBLWP8oVY6gad/view?usp=sharing) to download the app


## Features
1. Display the list of news categories.
2. Show news sources when user click one of the news category.
3. Show news articles when user click one of the news source.
4. Show the article detail on web view when user click one of the article.
5. Provide function to search news sources and news articles.

## Supported Device
- Android device with minimum API 16 **(Jelly Bean)**

## Build App requirements
- Recomended using Android Studio 4.1.2
- Using Kotlin 1.4.32

## Instructions
1. Clone from this repository
    - Copy repository url
    - Open your Android Studio
    - New -> Project from Version Control..
    - Paste the url, click OK
2. Replace demo api key with your [newsapi.org](newsapi.org) api key [here](https://github.com/CumiTerbang/MyNewsPortal/tree/master/app/src/main/java/com/haryop/mynewsportal/utils/ConstantsObj.kt)
    - utils > ConstantsObj > NEWSAPIORG_APIKEY
3. Prepare the Android Virtual Device or real device
4. Build and deploy the app module

## Code Design & Structure
This project is using MVVM design pattern. The project directory consist of 4 directories:
1. **data**: The M (Model) in MVVM. Where we perform data operations.
2. **di**: Dependency Injection directory with the help of Hilt.
3. **ui**: User Interface directory for Fragments and ViewModels helping to display data to the user.
4. **util**: Urilities directory for helper classes and functions.
