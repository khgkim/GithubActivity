This is where we build the nginx docker, with some pre-installed shells to do swapping. NGINX is a reverse proxy, which takes the request from outside and forwards it as indicated in the nginx.conf file

FILES:
===
  **Dockerfile**
    starts off with ubuntu, adds a bunch of tools, and then also
    nginx. Then addes the nginx conf, and a bunch of shell scripts.


  **Nginx.conf**
    configuration file. Note that it initially redirects to www.cs.ucdavis.edu as a dummy.
    This gets edited to point to web1 by init.sh


  **init.sh**
    changes the nginx conf file in the NGINX containers to point to web1.


  **swap2.sh**
    hot-swaps from web1 to web2.


  **swap1.sh**
    hotswaps from web2 to web1. 
