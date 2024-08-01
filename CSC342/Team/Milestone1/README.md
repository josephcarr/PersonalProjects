# [ParkQuest]
## Group [P]: Milestone 1


[ Milestone Report Here ]
### In Progress HTML:

## Wireframes:
![Web Display for ParkQuest](https://github.com/josephcarr/PersonalProjects/blob/main/CSC342/Team/Proposal/Wireframes/Finished/Web-%20wireframe%20V2.png)

| HTML PAGES           | % completed | TO DO                                                                   |
|----------------------|-------------|-------------------------------------------------------------------------|
| Template Page        | 80%         | - Adding user info to the top bar                                         |
| Login/Signup         | 100%        |                                                                         |
| Progress / Milestone | 70%         | - Add charts / combined progress and milestone pages                    |
| Find Park            | 20%         | - Have search bar, need to load parks                                     |
| Home Page            | 50%         | - Current camera library not working / link the folder button to images |
| Profile              | 80%         | - Add edit functionality                                                |
| Images               | 0%          |                                                                         |
| Add Friends          | 90%         |                                                                         |
| History              | 90%         | - Maybe combine with images                                             |
| Community            | 90%         | - Missing Dynamic data                                                  |

### In Progress APIs:

| Method | Route                             | Description                                      |
|--------|-----------------------------------|--------------------------------------------------|
| `POST` |  `/register `                       | Add the user to the system                       |
| `POST` |  `/login `                          | Log in the user to the system                    |
| `GET`  |  `/users`                         |  Returns a list of all users                     |
| `GET`  | `/users/:userId `                 |  Returns a users by id                           |
| `POST` | `/users/:userId/friends `          | Add a friend to the current user                 |
| `GET`  |` /users/:userId/friends `           | Returns a user friend's list                     |
| `POST` | `/users/:userId/visited `          | Add a visited park                               |
| `GET`  | `/users/:userId/visited`          | Returns a visited park's list                    |
| `GET`  | `/parks/:parkId`                  |  Returns a park by id                            |
| `GET`  | `/parks`                          | Returns all the parks from nps.gov               |
| `GET`  | `/national-parks/lat-long`        | Returns the park long and lat from nps.gov       |
| `GET`  | `/national-parks/:parkId`         | Returns a park info from nps.gov by id           |
| `GET`  | `/national-parks/:parkId/latlong` | Returns the park long and lat from nps.gov by id |


### Team Member Contributions

#### [Mauro Petrarulo]

* Setup enviroment part 1'
* Create Html pages: Template, Home Page, Progress, Profile, Images
* Investigated camera library and testing
* Serched for US national parks endpoints
* Created APIs related to national parks

#### [Kyle Curley]

* Created Login and Registration page
* Added form validation to register page
* Set up loading html content from the sidebar
* Wrote API for get parks from national parks endpoint
* Drafted APIs for User login and register and add friends and add park vistited

#### [Joseph Carrasco]

* Created Add Friends, History, and Community pages
* Upadted API Routes for adding friends to users and adding visited parks
* Added API Routes to get a user's friends list and visited parks

#### Milestone Effort Contribution

Mauro Petrarulo| Kyle Curley | Joseph Carrasco
-------------| ------------- | -------------
 35%           | 35%            | 30%


Note:Emoji link list

https://gist.github.com/rxaviers/7360908

### Meeting Notes
## Pages
Kyle - Template Page, Login/Signup, Milestone, Find Park
Mauro - Home Page, Progress, Profile, Images
Joseph - Add Friends, History, Community
