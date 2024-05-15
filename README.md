# Auction project
## About
This is the "Auction" project. In this project, there are 2 types of users: admin and user.

To allow a user to list their product on the auction, they write information about themselves and the product they want to sell in the "Contact Us" tab.

__Use case__:

After the user sends a request through "Contact Us", the administration responds to them via email and further clarifies and agrees on all details.
After all details are agreed upon, the administrator can add the product to their menu, enter: the email of the one who is listing, price, quantity, product status, and description (optional).

After the auction creation time is determined, the administrator creates the auction and sets the auction duration (minimum 7 days).

On the day the auction ends, the last person who placed a bid becomes the winner and the auction is put on pause. The user who wins has 3 days to pay for the auction. If, after the time limit, the winner does not pay for the auction, it is re-auctioned for a week ahead.
## Technologies
- Spring boot
- Hibernate
- PostgreSQL
- Maven
- Thymeleaf
- WebSocket
- Spring Security
- Javascript
  
## How to run application
There are 2 ways:
- Copy the repository and run it in your IDE, but:
    - You have to replace the database address in `"application.properties"` file with your own.
    - Change name of database in `"data.sql"` file to your own name.
    - Run application and go to `"http://localhost:8090/"`

- If you have docker you can just take `"docker-compose.yml"` file
    - Enter the command `"docker-compose up -d"` *(navigate in advance to the path where the file is located)* into the terminal. And wait for it download.
    - Open the browser and go to `"http://localhost:8090/"`.
    - If you are going to host on a different IP address, then you need to enter in the file in
  ```app:
       enviroment:
         "IP_ADDRESS": ""

## Access & Permissions
User with role __"USER"__ can do:

- can go to user page
- browse auctions
- bet on auctions
- check your products
- check your won actions
- pay for the auction
- update profile

User with role __"ADMIN"__ can do:

- can go to admin page
- check all users
- add/delete users
- check all products
- add/delete/update products
- add/delete/update auctions
- check all bids
