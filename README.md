Parking spot Gent
-----------------
The Parkingspots Gent app provides a portal to the locations of all public parking spots (no street parking) of the city Gent in Flanders/Belgium. Some of the listed parking spots are 'Park and Ride' type. The application allows to filter the list based on the type 'Parking' or 'Park and Ride'. For some of the 'Park and Ride' parking spots real time occupation is shown as well in an animated fashion. Each parking spot can be clicked to obtain more location details and to provide an intent button to open the location in Google Maps based on the lattitude en longitude coordinates of the location.

The invariant information of the parking spot locations is synchronized with a local database to provide an offline usage of the app and be able to get an overview of the parking spot locations when there is no internet. The city of Gent provides a RESTful api with endpoint https://data.stad.gent/api/explore/v2.1/catalog/datasets/locaties-openbare-parkings-gent/records as part of its open data policy.

The real time information is refreshed on a regular basis. The city of Gent provides a RESTful api with endpoint https://data.stad.gent/api/explore/v2.1/catalog/datasets/real-time-bezetting-pr-gent/records as part of its open data policy. At the time of development and publication of the app only the 'Park and Ride' spots 'P+R Bourgoyen', 'P+R Wondelgem' and 'P+R The Loop' effectively have updated info through the api. The 'Park and Ride' spots '"P+R Gentbrugge Arsenaal' and 'P+R Oostakker' fail to have updated info through the api because for some reason the Gent open data is not fed with updated info. The best way to observe the real time behavior of the app is to filter the list to only show 'Park and Ride' spots and position the list on the 'P+R Bourgoyen', 'P+R Wondelgem' and 'P+R The Loop' section of the list.


--------
The Dokka documentation can be found in the docs folder
