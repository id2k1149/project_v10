# project_v10

an Enterprise voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a diner and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which diner they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each diner provides a new menu each day.

#### add a new User
`curl -s -X POST -d '{"username":"newUser", "password":"password"}'
-H 'Content-Type:application/json;charset=UTF-8'
-L 'http://localhost:8080/api/v1/users/registration'`

#### add a new Diner
`curl -s -X POST -d '{"title":"New Diner"}' 
-H 'Content-Type:application/json;charset=UTF-8' 
-L 'http://localhost:8080/api/v1/diners' -u admin:password`

#### add a new Menu to Diner#3
`curl -s -X POST -d '{"details": { "New Toast": 35.92, "New Ribs": 63.85 } }' 
-H 'Content-Type:application/json;charset=UTF-8' 
-L 'http://localhost:8080/api/v1/diners/3/menu' -u admin:password`

#### add a new Menu to Diner#4
`curl -s -X POST -d '{"details": { "Pizza": 15.92, "Fish and Chips": 23.15 } }'
-H 'Content-Type:application/json;charset=UTF-8'
-L 'http://localhost:8080/api/v1/diners/4/menu' -u admin:password`

#### get today Menu from all Diners
`curl -s -L 'http://localhost:8080/api/v1/diners/today' -u user:password`

#### vote for Diner#3
`curl -X POST 
-H 'Content-Type:application/json;charset=UTF-8' 
-L 'http://localhost:8080/api/v1/diners/3/vote' -u user:password`

#### get today voting results
`curl -L 'http://localhost:8080/api/v1/counters/today' -u user:password`

#### get today best result
`curl -L 'http://localhost:8080/api/v1/counters/best' -u user:password`

#### get all history of how User#2 voted
`curl -L 'http://localhost:8080/api/v1/users/2/votes' -u admin:password`