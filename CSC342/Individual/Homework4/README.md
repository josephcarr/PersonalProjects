A challenge that I had with this project was using the data returned from the promises of the api calls. I had originally thought that I
could set a variable outside the scope of the promise call to the promises' return value, but since it was an async process, the variable
would be undefined if used after. To work around this, I did all the necessary work in the .then call to use the data returned from the promise.

In the future, I would add functionality for a user to have to input a password for authentication to make the site more secure. I would
also want to add a database to make this like an official app to save real users and howls.
