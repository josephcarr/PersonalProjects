const express = require('express');
const cookieParser = require('cookie-parser');

const apiRouter = express.Router();
apiRouter.use(express.json());
apiRouter.use(cookieParser());

const {SessionMiddleware, initializeSession, removeSession} = require('../middleware/SessionCookieMiddleware.js');

const UserDAO = require('./db/UserDAO.js');
const HowlDAO = require('./db/HowlDAO.js');
const FollowDAO = require('./db/FollowDAO.js');

// Check if it works
apiRouter.get('/', (req,  res) => {
    res.json({your_api: 'it works'});
});

// User Login
apiRouter.post('/login', (req, res) => {
    console.log("Logging");
    if (!req.body.userName || req.body.userName == '') {
        return res.status(400).json("No login information");
    }

    UserDAO.getUserByName(req.body.userName).then((user) => {
        initializeSession(req, res, user);
        res.json(user);
    }).catch(() => {
        res.status(401).json('User not found');
    });
});

//get current user
apiRouter.get('/users/current', SessionMiddleware, (req,  res) => {
    res.json(req.session.user);
});

// Get all users
apiRouter.get('/users', SessionMiddleware, (req, res) => {
    UserDAO.getUsers().then(users => {
        res.json(users);
    }).catch(err => {
        res.status(500).json({error: 'Internal server error'});
    });

});

//Get specific user by id
apiRouter.get('/users/id/:userId', SessionMiddleware, (req, res) => {
    const userId = parseInt(req.params.userId);

    UserDAO.getUserById(userId).then(user => {
        res.json(user)
    }).catch(() => {
        res.status(404).json('User not found');
    });
});

//Get specific user by user name
apiRouter.get('/users/usr/:userName', SessionMiddleware, (req, res) => {
    UserDAO.getUserByName(req.params.userName).then((user) => {
        res.json(user);
    }).catch(() => {
        res.status(404).json('User not found');
    });
});

// Get follows
apiRouter.get('/follows', SessionMiddleware, (req, res) => {
    FollowDAO.getFollows().then(follows => {
        res.json(follows)
    });
});

// Adding a follow
apiRouter.put('/users/id/:userId/following', SessionMiddleware, (req, res) => {
    FollowDAO.putFollow(req.body).then((follow) => {
        res.json(follow);
    }).catch(() => {
        res.status(500).json({error: 'Internal server error'});
    });
});

// Get a User's following
apiRouter.get('/follows/usr/:userId', SessionMiddleware, (req, res) => {
    const userId = parseInt(req.params.userId);

    FollowDAO.getFollowsByUser(userId).then(follows => {
        res.json(follows)
    }).catch(() => {
        res.status(404).json('Follows not found');
    });
});

// Get howls
apiRouter.get('/howls', SessionMiddleware, (req, res) => {
    HowlDAO.getHowls().then(howls => {
        res.json(howls)
    });
});

// Get howls by ID
apiRouter.get('/howls/id/:howlId', SessionMiddleware, (req, res) => {
    const howlId = parseInt(req.params.howlId);
    HowlDAO.getHowlById(howlId).then(howl => {
        res.json(howl)
    }).catch(() => {
        res.status(404).json('Howls not found');
    });
});

// Create a new howl
apiRouter.post('/howls', SessionMiddleware, (req, res) => {
    HowlDAO.createHowl(req.body).then((howl) => {
        res.json(howl);
    }).catch(() => {
        res.status(500).json({error: 'Internal server error'});
    });
});

// Get howls posted by user only
apiRouter.get('/howls/usr/:userId', SessionMiddleware, (req, res) => {
    const userId = parseInt(req.params.userId);

    HowlDAO.getHowlsByUser(userId).then(howls => {
        res.json(howls)
    }).catch(() => {
        res.status(404).json('Howls not found');
    });
});

// Get howls posted by user and followers
apiRouter.get('/howls/:userId', SessionMiddleware, (req, res) => {
    const userId = parseInt(req.params.userId);

    HowlDAO.getHowlsForUser(userId).then(howls => {
        res.json(howls)
    }).catch(() => {
        res.status(404).json('Howls not found');
    });
});

module.exports = apiRouter;