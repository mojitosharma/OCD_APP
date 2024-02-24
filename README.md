To Run the Backend on the VM:
1. cd to the ocd_backend folder
2. run: mvn clean install
2. then run: nohup java -jar -Dserver.port=8081 target/ocd_backend.jar > output.log 2>&1 &
