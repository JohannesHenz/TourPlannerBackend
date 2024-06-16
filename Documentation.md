# Tourplanner Documentation

## Protocol

### Setup des Backends:
Man braucht die latest version von node, die latest version von puppeteer. Man muss einen User “tourplanner_user” in psql anlegen mit Passwort “password”.

### Unique Feature:
The user is also presented with the average time it took other people to complete the tour.

## Frontend:
The Frontend is made up of multiple windows. The “Hello” window is responsible for almost everything. It displays a list of all the tours, all the data of the selected tour and their respective tourlogs and allows for full-text search. It also manages deleting tours/tourlogs but adding/editing either creates a popup.

There are 4 popup windows in total, although they're quite similar in design: Add Tour, Edit Tour, Add TourLog, and Edit TourLog. The respective add and edit windows are pretty much identical with the only difference being the headline and the fact that the fields are prefilled with the already present data for edit, which is why they share all of their classes. So for the context of the code, there are really just 2 popup windows, one for tours and one for tourlogs.

## View:
Our program features an FXML file for each of those 3 windows. The elements in these views are linked to their respective controller classes: `HelloController`, `PopupController` (for tours), and `LogPopupController`. These controller classes are responsible for the display the users sees, like full-text search, error popups, and controlling which logs are shown depending on the selected tour. Any inputs that lead to business logic being executed, however, they forward to their respective ViewModel class. For this purpose, they also execute databinds for all the needed variables.

## Model:
Most classes in our project's frontend are dedicated to the Model. Here's a quick list of them with the tasks they're responsible for:

- **Tour:** Basic class representing a tour with all its fields except for images which are handled separately.
- **TourLogs:** Represents a TourLog, knows which Tour it belongs to by saving its ID.
- **TourManager:** This saves and manages a list of all Tours. This class is an instance, so we have a single global representative, allowing us to access all Tours from anywhere. Changes here typically also call methods from `BackendService` to manipulate the Backend.
- **TourLogManager:** The TourLogs equivalent to `TourManager`.
- **BackendService:** Manages all the calls and responses to the frontend and manipulating the managers.
- **PDFHandler:** Manages all writing of PDFs as well as reading them in, as they act to import/export file data as well as creating reports simultaneously.
- **Mixin-Classes:** Helper classes for omitting certain attributes when object mapping, because `@JsonIgnore` couldn't always be used.
- **Image classes:** Pretty much the equivalent to Tours, but just managing the image.

## ViewModel:
Consists of `HelloViewModel`, `TourViewModel`, and `LogViewModel`. When the data in the model needs to be altered, like editing a Tour, this function then calls the respective function from the model to do this, like updating Manager instances or calling the PDF methods.

## Backend:
The backend of the Tourplanner project is designed to handle the core business logic and data persistence. It is built using Spring Boot and Postgres.

### Controllers:
These handle incoming HTTP requests and route them to the appropriate services. For instance, `TourController` manages requests related to tours, such as creating, updating, deleting, and fetching tour details.

### Services:
These contain the business logic of the application. They interact with the repositories to perform CRUD operations and other complex operations. Key services include:
- **TourService:** Manages tour-related operations.
- **TourLogService:** Handles operations related to tour logs.
- **RouteService:** Calculates routes and interacts with external APIs for geocoding and routing.
- **MapImageService:** Manages downloading and deletion of map images related to tours.

### Repositories:
These are interfaces that extend Spring Data JPA’s `JpaRepository`, providing a layer of abstraction over the database. They handle data persistence and retrieval.

### Models:
Represent the data structure of the application. Key models include:
- **Tour:** Represents a tour with fields such as name, fromLocation, toLocation, transportType, etc.
- **TourLog:** Represents a log entry for a tour, containing information like date, duration, distance, and comments.
- **RouteInfo:** Represents routing information including distance and duration.
- **RouteResponse:** Handles responses from the routing API.

### Workflow:
When a user creates a new tour through the frontend, the following steps occur in the backend:
1. The request is sent to the `TourController`, which validates the input and forwards it to the `TourService`.
2. `TourService` processes the request, calling `RouteService` to fetch routing information.
3. Once the route information is retrieved, `TourService` saves the tour details to the database via `TourRepository`.
4. The `MapImageService` is called to download a map image for the tour route.
5. Finally, the new tour details are returned to the frontend.
