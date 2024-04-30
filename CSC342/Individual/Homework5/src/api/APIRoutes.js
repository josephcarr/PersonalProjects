const express = require('express');
const cookieParser = require('cookie-parser');

const apiRouter = express.Router();
apiRouter.use(express.json());
apiRouter.use(cookieParser());

const { TokenMiddleware, generateToken, removeToken } = require('../middleware/TokenMiddleware.js');

const UserDAO = require('./db/UserDAO.js');
const HowlDAO = require('./db/HowlDAO.js');
const FollowDAO = require('./db/FollowDAO.js');

// Check if it works
apiRouter.get('/', (req, res) => {
    res.json({ your_api: 'it works' });
});

// User Login
apiRouter.post('/login', (req, res) => {
    if (req.body.username && req.body.password) {
        UserDAO.getUserByCredentials(req.body.username, req.body.password).then(user => {
            let result = {
                user: user
            }

            generateToken(req, res, user);

            res.json(result);
        }).catch(err => {
            console.log(err);
            res.status(err.code).json({ error: err.message });
        });
    }
    else {
        res.status(401).json({ error: 'Not authenticated' });
    }
});

apiRouter.post('/logout', (req, res) => {
    removeToken(req, res);

    res.json({ success: true });
});

apiRouter.post('/register', (req, res) => {
    if (req.body.username && req.body.password) {
        UserDAO.createUser(req.body.username, req.body.first_name, req.body.last_name, req.body.password).then(user => {
            let result = {
                user: user
            }

            FollowDAO.registerUserFollows(user).then((follows) => {
                console.log(follows);
            });

            res.json(result);
        }).catch(err => {
            console.log(err);
            res.status(err.code).json({ error: err.message });
        });
    }
    else {
        res.status(404).json({ error: 'Error creating user' });
    }
});

//get current user
apiRouter.get('/users/current', TokenMiddleware, (req, res) => {
    res.json(req.user);
});

// Get all users
apiRouter.get('/users', TokenMiddleware, (req, res) => {
    UserDAO.getUsers().then(users => {
        res.json(users);
    }).catch(err => {
        res.status(500).json({ error: 'Internal server error' });
    });

});

//Get specific user by id
apiRouter.get('/users/id/:userId', TokenMiddleware, (req, res) => {
    const userId = parseInt(req.params.userId);

    UserDAO.getUserById(userId).then(user => {
        res.json(user)
    }).catch(() => {
        res.status(404).json('User not found');
    });
});

//Get specific user by user name
apiRouter.get('/users/usr/:userName', TokenMiddleware, (req, res) => {
    UserDAO.getUserByName(req.params.userName).then((user) => {
        res.json(user);
    }).catch(() => {
        res.status(404).json('User not found');
    });
});

// Get follows
apiRouter.get('/follows', TokenMiddleware, (req, res) => {
    FollowDAO.getFollows().then(follows => {
        res.json(follows)
    });
});

// Adding a follow
apiRouter.put('/users/id/:userId/following', TokenMiddleware, (req, res) => {
    FollowDAO.putFollow(req.body).then((follow) => {
        res.json(follow);
    }).catch(() => {
        res.status(500).json({ error: 'Internal server error' });
    });
});

// Get a User's following
apiRouter.get('/follows/usr/:userId', TokenMiddleware, (req, res) => {
    const userId = parseInt(req.params.userId);

    FollowDAO.getFollowsByUser(userId).then(follows => {
        res.json(follows)
    }).catch(() => {
        res.status(404).json('Follows not found');
    });
});

// Get howls
apiRouter.get('/howls', TokenMiddleware, (req, res) => {
    HowlDAO.getHowls().then(howls => {
        res.json(howls)
    });
});

// Get howls by ID
apiRouter.get('/howls/id/:howlId', TokenMiddleware, (req, res) => {
    const howlId = parseInt(req.params.howlId);
    HowlDAO.getHowlById(howlId).then(howl => {
        res.json(howl)
    }).catch(() => {
        res.status(404).json('Howls not found');
    });
});

// Create a new howl
apiRouter.post('/howls', TokenMiddleware, (req, res) => {
    HowlDAO.createHowl(req.body).then((howl) => {
        res.json(howl);
    }).catch(() => {
        res.status(500).json({ error: 'Internal server error' });
    });
});

// Get howls posted by user only
apiRouter.get('/howls/usr/:userId', TokenMiddleware, (req, res) => {
    const userId = parseInt(req.params.userId);

    HowlDAO.getHowlsByUser(userId).then(howls => {
        res.json(howls)
    }).catch(() => {
        res.status(404).json('Howls not found');
    });
});

// Get howls posted by user and followers
apiRouter.get('/howls/:userId', TokenMiddleware, (req, res) => {
    const userId = parseInt(req.params.userId);

    HowlDAO.getHowlsForUser(userId).then(howls => {
        res.json(howls)
    }).catch(() => {
        res.status(404).json('Howls not found');
    });
});

module.exports = apiRouter;