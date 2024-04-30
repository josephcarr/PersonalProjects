let follows = require('./data/follows.json');
const UserDAO = require('./UserDAO');

module.exports = {
    getFollows: () => {
        return new Promise((resolve, reject) => {
            resolve(Object.values(follows));
        })
    },

    getFollowsByUser: (id) => {
        return new Promise((resolve, reject) => {
            let userFollow = Object.values(follows).find((follow) => follow.userId == id);
            console.log(userFollow);

            followedUsers = getFollowers(userFollow);

            if (followedUsers) {
                resolve(followedUsers);
            }
            else {
                reject(`Follows don't exist`);
            }
        })
    },

    putFollow: (followData) => {
        return new Promise((resolve, reject) => {
            let userFollow = Object.values(follows).find((follow) => follow.userId == followData.currentId);

            if (userFollow.following.find((followerId) => followerId == followData.followerId)) {
                const index = userFollow.following.indexOf(followData.followerId);
                if (index > -1) {
                    userFollow.following.splice(index, 1);
                }
            }
            else {
                userFollow.following.push(followData.followerId);
            }

            resolve(userFollow);
        })
    },

    registerUserFollows: (user) => {
        return new Promise((resolve, reject) => {
            const newFollows = {
                userId: user.id,
                following: [1, 2]
            }

            follows.push(newFollows);

            resolve(follows);
        })
    }
};

function getFollowers(userFollow) {
    followedUsers = [];
    userFollow.following.forEach((follower => {
        UserDAO.getUserById(follower).then(user => {
            followedUsers.push(user);
        });
    }));

    return followedUsers;
}