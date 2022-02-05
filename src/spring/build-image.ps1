Write-Output "-> Building spring app"
.\gradlew build

Write-Output "-> Building image"
docker build -t d3ds3g/authme-spring-worker .

Write-Output "-> Pushing image"
docker push d3ds3g/authme-spring-worker