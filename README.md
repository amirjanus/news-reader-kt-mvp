Simple app made up of two screens.

First screen shows list of articles from NewsApi. Articles are saved local database. If locally saved articles exist and are not older than 5 minutes they will be shown, otherwise the articles will be fetched remotely from the server.

If there is an error while making request to NewsApi an alert will be shown.

When user on one of the articles in the list a second screen with carousel of articles will be shown.

<div float="left">
  <img src="https://github.com/amirjanus/assets/blob/master/news-reader-kt-mvp/Screenshot_1593775155.png" width="200" />
  <img src="https://github.com/amirjanus/assets/blob/master/news-reader-kt-mvp/Screenshot_1593775338.png" width="200" />
  <img src="https://github.com/amirjanus/assets/blob/master/news-reader-kt-mvp/Screenshot_1593775198.png" width="200" />
</div>

How to run app

1. Add “news_api_key.xml” file in values resources directory and inside it add a "news_api_key" string with NewsApi key as value.
