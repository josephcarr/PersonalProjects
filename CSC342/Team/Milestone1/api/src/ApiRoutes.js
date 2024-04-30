const apiRouter = require('express').Router();
const axios = require('axios');

const {users, parks} = require('./db/data/data.js');

// Check if it works
apiRouter.get('/', (req,  res) => {
    res.json({your_api: 'it works'});
});

// Rough draft of what the register route will look like
apiRouter.post('/register', (req, res) => {
    try {
        const { userName, password, firstName, lastName, email } = req.body;

        if (Object.values(users).some(user => user.userName === userName)) {
            return res.status(409).json({ error: "Username already taken" });
        }

        const userId = Object.keys(users).length + 1;

        const newUser = {
            id: userId,
            userName,
            password,
            firstName,
            lastName,
            email,
            friends: [],
            parksVisited: [],
            numParksVisited: 0
        };

        users[userId] = newUser;

        res.status(201).json({ message: "User registered successfully" });
    } catch (error) {
        console.error("Error registering user:", error);
        res.status(500).json({ error: "Internal server error" });
    }
});

// Rough draft of what the login route will look like
apiRouter.post('/login', (req, res) => {
    try {
        const { userName, password } = req.body;

        const user = Object.values(users).find(user => user.userName === userName);

        if (!user || user.password !== password) {
            return res.status(401).json({ error: "Invalid username or password" });
        }

        res.json({ user, message: "Sign-in successful" });
    } catch (error) {
        console.error("Error signing in user:", error);
        res.status(500).json({ error: "Internal server error" });
    }
});

// Get all users
apiRouter.get('/users', (req, res) => {
    res.json(Object.values(users));
});

//Get specific user
apiRouter.get('/users/:userId', (req, res) => {
    const user = users[req.params.userId];
    if(user) {
        res.json(user);
    }
    else {
        res.status(404).json({error: "User not found"});
    }
});

// Rough draft of adding a friend
apiRouter.post('/users/:userId/friends', (req, res) => {
    const { userId } = req.params;
    const { friendId } = req.body;

    // Check if both user and friend exist
    if (!users[userId] || !users[friendId]) {
        return res.status(404).json({ error: "User or friend not found" });
    }

    // Check if the friend is not already in the user's friends list
    if (users[userId].friends.includes(friendId)) {
        return res.status(400).json({ error: "Friend already added" });
    }

    // Add friend to user's friends list
    users[userId].friends.push(friendId);

    res.json({ message: "Friend added successfully" });
});

// Get a User's Friends
apiRouter.get('/users/:userId/friends', (req, res) => {
    const { userId } = req.params;

    // Check if both user and friend exist
    if (!users[userId]) {
        return res.status(404).json({ error: "User not found" });
    }

    res.json({ friends: users[userId].friends });
});

// Rough draft of marking a park as visited
apiRouter.post('/users/:userId/visited', (req, res) => {
    const { userId } = req.params;
    const { parkName } = req.body;

    // Check if the user exists
    if (!users[userId]) {
        return res.status(404).json({ error: "User not found" });
    }

    // Increment the number of parks visited
    users[userId].numParksVisited++;

    // Add the park to the list of parks visited
    users[userId].parksVisited.push(parkName);

    res.json({ message: "Park marked as visited successfully" });
});

// Get a User's Visited Parks
apiRouter.get('/users/:userId/visited', (req, res) => {
    const { userId } = req.params;

    // Check if both user and friend exist
    if (!users[userId]) {
        return res.status(404).json({ error: "User not found" });
    }

    res.json({ parksVisited: users[userId].parksVisited });
});

apiRouter.get('/parks/:parkId', (req, res) => {
    const park = parks[req.params.parkId];
    if(park) {
        res.json(park);
    }
    else {
        res.status(404).json({error: "Park not found"});
    }
});

// Get all parks
apiRouter.get('/parks', (req, res) => {
    res.json(Object.values(parks));
});

apiRouter.get('/national-parks', async (req, res) => {
    try {
        const response = await axios.get('https://developer.nps.gov/api/v1/parks', {
            params: {
                api_key: 'b8WE4bBaiZxiJs7o5YHdAyrfjWz9sPsBPP02dWGT'
            }
        });
        const parks = response.data.data; 
        res.json(parks);
    } catch (error) {
        console.error('Error fetching parks:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

//getting latitude and longitude for a park
apiRouter.get('/national-parks/lat-long', async (req, res) => {
    try {
        const response = await axios.get('https://developer.nps.gov/api/v1/parks', {
            params: {
                api_key: 'b8WE4bBaiZxiJs7o5YHdAyrfjWz9sPsBPP02dWGT'
            }
        });

        const parks = response.data.data;

        // Extracting latitude and longitude from the first park in the array
        const firstPark = parks[0];
        const latitude = firstPark.latitude;
        const longitude = firstPark.longitude;
        res.json({ latitude, longitude });
    } catch (error) {
        console.error('Error fetching latitude and longitude:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

// Get a specific park by parkId
http://localhost:3000/api/national-parks/A21F01BC-9E47-4DBE-8655-A1651FC2C1B1
apiRouter.get('/national-parks/:parkId', async (req, res) => {
    const parkId = req.params.parkId;
    try {
        const response = await axios.get(`https://developer.nps.gov/api/v1/parks`, {
            params: {
                api_key: 'b8WE4bBaiZxiJs7o5YHdAyrfjWz9sPsBPP02dWGT'
            }
        });

        const parkData = response.data.data;
        // Find the park with the provided parkId
        const park = parkData.find(park => park.id === parkId);
        if (!park) {
            // Park not found
            return res.status(404).json({ error: 'Park not found' });
        }

        res.json(park);
    } catch (error) {
       // console.error('Error fetching park information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});



// Get the latitude and longitude from a  specific park by parkId
//http://localhost:3000/api/national-parks/A21F01BC-9E47-4DBE-8655-A1651FC2C1B1/latlong
apiRouter.get('/national-parks/:parkId/latlong', async (req, res) => {
    const parkId = req.params.parkId;
    try {
        const response = await axios.get(`https://developer.nps.gov/api/v1/parks`, {
            params: {
                api_key: 'b8WE4bBaiZxiJs7o5YHdAyrfjWz9sPsBPP02dWGT'
            }
        });

        const parkData = response.data.data;
        // Find the park with the provided parkId
        const park = parkData.find(park => park.id === parkId);
        if (!park) {
            // Park not found
            return res.status(404).json({ error: 'Park not found' });
        }
        const latitude = park.latitude;
        const longitude = park.longitude;
        res.json({ latitude, longitude });
    } catch (error) {
       // console.error('Error fetching park information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

module.exports = apiRouter;

