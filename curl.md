#1 add a new User
curl -s -X POST -d '{"username":"newUser", "password":"password"}' \
-H 'Content-Type:application/json;charset=UTF-8' \
-L 'http://localhost:8080/api/v1/users'

#2 add a new Answer/Restaurant
curl -s -X POST -d '{"title":"New Diner"}' \
-H 'Content-Type:application/json;charset=UTF-8' \
-L 'http://localhost:8080/api/v1/answers' -u admin:password

#3 add a new Info/Menu to Answer#3/Restaurant#3
curl -s -X POST -d '{"details": { "New Toast": 35.92, "New Ribs": 63.85 } }' \
-H 'Content-Type:application/json;charset=UTF-8' \
-L 'http://localhost:8080/api/v1/answers/3/info' -u admin:password

#4 get today Info/Menu from all Answers/Restaurants 
curl -s -L 'http://localhost:8080/api/v1/answers/today' -u user:password

#5 vote for Answer#3/Restaurant#3
curl -s -X POST -d  \
-H 'Content-Type:application/json;charset=UTF-8' \
http://localhost:8080/api/v1/answers/3/vote -u user:password

#6 get today voting results
curl -L 'http://localhost:8080/api/v1/votes/today' -u user:password

#7 get today best result
curl -L 'http://localhost:8080/api/v1/votes/best' -u user:password