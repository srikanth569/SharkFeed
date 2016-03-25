# SharkFeed

Explanation of the architecture.
The SharkFeed app is built with an idea that its an Image View application rather than just a Image Search App. The App is built with a database centric approach and all the data is written to the Database before its displayed to the user. This way, even when the user comes back to the app after initial load, he still has the information from the previous request. 
Each subsequent request appends records to the database and the UI is notified regarding the new data. This approach basically makes the UI a simple implementation that just reads from the database and does not worry about loading more data itself. Request to load more data is triggered when we move past the half way mark of the total available items. 

Due to time limitations, I made the following assumptions while building this app

Assumptions made
_ _ _ _ _ _ _ _ _ _ _ _ _ _ _
 
1 . The app is always connected to network.\n
2 . Using only images that have urls for medium, large and original.\n
3 . No rate limiting logic in place. \n
4 . No Check to make sure device has enough memory to download image. \n
5 . Original photo was not available for a lot of images coming down the API, so saving the large image instead. \n

