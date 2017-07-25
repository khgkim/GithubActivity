The files in this directory:

docker-compose.yml
  This file brings up two containers. one with nginx (see contained directory nginx-rev for how
  that container is built. The nginx container has the facilities to execute the swap
  Which port is the ngnix listening on? Why is it mapped?
  Why is the port on the other container not mapped? Can it be accessed from outside
  the container?

cleanup.sh (no arguments)
  Good idea to execute this once in a while, to clean up "zombie" docker images that are halted

dorun.sh (no arguments)
  1) This shell uses the yml file to bring up the docker network
  2) When the nginx comes up, it's pointing at the UC Davis engineering school.
  3) Once the network comes up, it tells the nginx container, using the "init.sh" shell
  script (see the directory below) to redirect the requests to the actual working directory.
  4) Note that the dorun.sh uses docker-compose command.

doswap.sh (one argument, the docker image, say DDDD, you want to run)
  1) If the current "live" container is named web1, then, it kills any existing one named
      web2 brings up the new DDD named web2.
  2) It tells the nginx to send the requests henceforth to web2. (using the swap1 shell script)
  3) It kills the web1
  4) If the otherway around web1<->web2 using Swap2 shell script.

Summary:

  1) Implement the extended servlet (war file).

  2) The bash scripts swap1.sh and swap2.sh does a hot swap.

  3) doswap.sh is a single shell script that does the swap regardless of how many ever times you try it.
     Clearly this shell script will run on the host machine, but will reach into the guts of the NGINX docker, and
     make the swap.
