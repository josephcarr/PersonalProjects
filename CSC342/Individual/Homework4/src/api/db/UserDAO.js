let users = require('./data/users.json');

module.exports = {
    getUsers: () => {
        return new Promise((resolve, reject) => {
            resolve(Object.values(users));
        })
    },

    getUserById: (id) => {
        return new Promise((resolve, reject) => {
            let user = Object.values(users).find((user) => user.id == id);

            if (user) {
                resolve(user);
            }
            else {
                reject(`User doesn't exist`);
            }
        })
    },

    getUserByName: (userName) => {
        return new Promise((resolve, reject) => {
            let user = Object.values(users).find((user) => user.username == userName);

            if (user) {
                resolve(user);
            }
            else {
                reject(`User doesn't exist`);
            }
        })
    }
}