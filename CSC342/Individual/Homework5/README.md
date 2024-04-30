A challenge that I had with this project was using creating funstionality for a new user. I had forgotten that the user follows json had a follows list for every user
so when i first created a user, my FollowDAO didnt have any follow information for the new user so it crashed.

All of my pages go through middleware verification so if you are no logged in, you cant access any of the pages. However, i may have redundant api routes so users can see how many users there are, who they follow, and all the howls in the system. I do have some security with users' passwords being stored as hashed passwords
and not as the plaintext they input so users cant guess passwords.
