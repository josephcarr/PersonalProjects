let howls = require('./data/howls.json');
const FollowDAO = require('./FollowDAO');

module.exports = {
    getHowls: () => {
        return new Promise((resolve, reject) => {
            resolve(Object.values(howls));
        })
    },

    getHowlById: (id) => {
        return new Promise((resolve, reject) => {
            let howl = Object.values(howls).find((howl) => howl.id == id);

            if (howl) {
                resolve(howl);
            }
            else {
                reject(`Howl doesn't exist`);
            }
        })
    },

    getHowlsByUser: (userId) => {
        return new Promise((resolve, reject) => {
            userHowls = [];
            Object.values(howls).forEach((howl) => {
                if (howl.userId == userId) {
                    userHowls.push(howl);
                }
            });

            if (userHowls) {
                resolve(userHowls);
            }
            else {
                reject(`Howls don't exist`);
            }
        })
    },

    getHowlsForUser: (userId) => {
        return new Promise((resolve, reject) => {
            userHowls = [];
            Object.values(howls).forEach((howl) => {
                if (howl.userId == userId) {
                    userHowls.push(howl);
                }
            })

            FollowDAO.getFollowsByUser(userId).then((followers) => {
                followers.forEach((follower) => {
                    Object.values(howls).forEach((howl) => {
                        if (howl.userId == follower.id) {
                            userHowls.push(howl);
                        }
                    })
                })
            });


            if (userHowls) {
                resolve(userHowls);
            }
            else {
                reject(`Howls don't exist`);
            }
        })
    },

    createHowl: (newHowl) => {
        return new Promise((resolve, reject) => {
            console.log(newHowl);
            let userId = newHowl.userId;
            let text = newHowl.text;

            let id = howls[howls.length - 1].id + 1;

            const howl = {
                id: id,
                userId: userId,
                datetime: new Date(),
                text: text
            };

            howls.push(howl);

            resolve(howl);
        })
    }
}