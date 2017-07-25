![Alt text](images/GithubActivity.png?raw=true)
![Alt text](images/Activities.png?raw=true)

GithubActivity
===
Displays user’s pull and merged pull-requests through [GitHub API](https://developer.github.com/v3/).

Server is deployed with Intellij IDE using Apache Tomcat.

Scripts
===
Bash shell scripts that automate server hot-swaps given an Docker image as its parameter

**doswap.sh**

* This shell script automates the hot-swap using swap1.sh/swap2.sh given an image as its parameter.
* First, it creates a new container with the given image, then executes one of the swap script under the bin directory within the ng container, and lastly removes the old container.  
* Steps to demonstrate hot-swap:

  ```
  $ ./dorun.sh
  $ ./doswap.sh <image name>  
  ```
