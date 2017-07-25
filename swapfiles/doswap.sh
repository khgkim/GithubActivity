#/bin/bash

FILE=$1

function newcontainer {
    CONTAINER=$(docker ps | grep "web" | awk '{print $NF}')
    COMPARE1="ecs189_web1_1"
    COMPARE2="web1"

    if [ $CONTAINER == $COMPARE1 -o $CONTAINER == $COMPARE2 ]
     then
     CONTAINER="web2"
     KILL="web1"
    else
     CONTAINER="web1"
     KILL="web2"
    fi

    docker run -d --network ecs189_default --name $CONTAINER $FILE
}

function hotswap1 {
    docker exec $(docker ps | grep "ng" | awk '{print $1}') /bin/bash /bin/swap1.sh
}

function hotswap2 {
    docker exec $(docker ps | grep "ng" | awk '{print $1}') /bin/bash /bin/swap2.sh
}

function killcontainer {
    docker rm -f $(docker ps | grep "$KILL" | awk '{print $1}')
}

# Command line usage
if [ "$#" -ne 1 ] 
    then
    echo "Usage: ./doswap.sh image"
else
    # Bring up a new container, hot-swap, and kill old container
    echo "Firing up new container"
    sleep 2 && newcontainer

    echo "Hot-swapping..."

    if [ $CONTAINER == $COMPARE1 -o $CONTAINER == $COMPARE2 ]
     then
     sleep 2 && hotswap1
    else
     sleep 2 && hotswap2
    fi

    echo "Removing old container"
    sleep 2 && killcontainer

    echo "Successful!"
fi

