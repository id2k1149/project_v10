# Diner Voting

an Enterprise voting system for deciding where to have lunch.

2 types of users: admin and regular users

Admin can input a diner and it's lunch menu of the day (2-5 items usually, just a menu name and price)

Menu changes each day (admins do the updates)

Users can vote on which diner they want to have lunch at

Only one vote counted per user

If user votes again the same day:

If it is before 11:00 we assume that he changed his mind.

If it is after 11:00 then it is too late, vote can't be changed

Each diner provides a new menu each day.

---------------------------------------
After first launch you get 
- admin and user1 with encoded "password"
- 2 fake diners with fake menu
- vote history of each user (including user1's today voice)
---------------------------------------

#### Swagger REST API Documentation (credentials - admin/password)
`http://localhost:8080/swagger-ui.html`

#### add a new User
`curl -X POST -d '{"username":"newUser", "password":"password"}'
-H 'Content-Type:application/json;charset=UTF-8'
-L 'http://localhost:8080/api/v1/users/registration'`

#### add a new Diner
`curl -X POST -d '{"title":"New Diner"}' 
-H 'Content-Type:application/json;charset=UTF-8' 
-L 'http://localhost:8080/api/v1/diners' -u admin:password`

#### add a new Menu to Diner#3
`curl -X POST -d '{"dishAndPrice": { "New Toast": 35.92, "New Ribs": 63.85 } }' 
-H 'Content-Type:application/json;charset=UTF-8' 
-L 'http://localhost:8080/api/v1/menus/3' -u admin:password`

#### get today Menu from all Diners
`curl -L 'http://localhost:8080/api/v1/diners/today' -u user1:password`

#### vote for Diner#3
`curl -X POST 
-H 'Content-Type:application/json;charset=UTF-8' 
-L 'http://localhost:8080/api/v1/results/3' -u user1:password`

#### get today voting results
`curl -L 'http://localhost:8080/api/v1/results/today' -u user1:password`

#### get today best result
`curl -L 'http://localhost:8080/api/v1/results/best' -u user1:password`

#### get all history of how User#1 voted
`curl -L 'http://localhost:8080/api/v1/users/1/history' -u admin:password`

#### get all history of menu for diner#3
`curl -L 'http://localhost:8080/api/v1/diners/3/history' -u user1:password`