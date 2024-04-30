const crypto = require('crypto');
let users = require('./data/users.json');

module.exports = {
    getUsers: () => {
        return new Promise((resolve, reject) => {
            resolve(users);
        })
    },

    getUserById: (id) => {
        return new Promise((resolve, reject) => {
            let user = users.find((user) => user.id == id);

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
            let user = users.find((user) => user.username == userName);

            if (user) {
                resolve(user);
            }
            else {
                reject(`User doesn't exist`);
            }
        })
    },

    getUserByCredentials: (username, password) => {
        return new Promise((resolve, reject) => {
            const user = users.find(user => user.username == username);
            if (user) { // we found our user
                crypto.pbkdf2(password, user.salt, 100000, 64, 'sha512', (err, derivedKey) => {
                    if (err) { //problem computing digest, like hash function not available
                        reject({ code: 400, message: "Error: " + err });
                    }

                    const digest = derivedKey.toString('hex');
                    if (user.password == digest) {
                        resolve(getFilteredUser(user));
                    }
                    else {
                        reject({ code: 401, message: "Invalid username or password" });
                    }
                });
            }
            else { // if no user with provided username
                reject({ code: 401, message: "No such user" });
            }
        })

    },

    createUser: (username, firstName, lastName, password) => {
        return new Promise((resolve, reject) => {
            const user = users.find(user => user.username == username);
            let id = users[users.length - 1].id + 1;

            if (!user) { // User is not in system
                const salt = crypto.randomBytes(32).toString ("hex");

                crypto.pbkdf2(password, salt, 100000, 64, 'sha512', (err, derivedKey) => {
                    if (err) { //problem computing digest, like hash function not available
                        reject({ code: 400, message: "Error: " + err });
                    }

                    const userPass = derivedKey.toString('hex');
                    
                    const user = {
                        id: id,
                        first_name: firstName,
                        last_name: lastName,
                        username: username,
                        avatar: "",
                        salt: salt,
                        password: userPass
                    };

                    users.push(user);

                    resolve(user);
                });
            }
            else { // if no user with provided username
                reject({ code: 401, message: "User already exists" });
            }
        })
    }
};

function getFilteredUser(user) {
    return {
        "id": user.id,
        "first_name": user.first_name,
        "last_name": user.last_name,
        "username": user.username,
        "avatar": user.avatar
    }
}