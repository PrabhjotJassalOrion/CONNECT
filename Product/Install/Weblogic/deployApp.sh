# set the CONNECT and WAS env variables
.  /nhin/CI/setVariables.sh

# deploy the application. The following parameters are passed:
# param 1 --> WLS userName
# param 2 --> WLS password
# param 3 --> WLS Admin Server URL
# param 4 --> Application Name
# param 5 --> Server Name
# param 6 --> ear File Name  
/nhin/weblogic/wlserver/common/bin/wlst.sh /nhin/CI/deployApp.py $ADMIN_USERNAME $ADMIN_PASSWORD $ADMIN_URL $APPLICATION_NAME $1 $EARFILENAME

sleep 120
