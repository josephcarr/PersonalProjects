import api from './APIClient.js';

// Function to fetch and display users
function displayUsers(current, users, following, followers) {
    users.sort((a, b) => {
        if (a.last_name.toLowerCase() < b.last_name.toLowerCase()) {
            return -1;
        }
        else {
            return 1;
        }
    });

    const usersContainer = document.getElementById('usersContainer');
    usersContainer.innerHTML = ''; // Clear previous content
    users.forEach(user => {
        if (current.id != user.id) {
            const userElement = document.createElement('div');
            userElement.classList.add('card', 'mb-3');

            const elementBody = document.createElement('div');
            elementBody.classList.add('card-body');
            elementBody.classList.add('userCard');

            // Title
            const userTitle = document.createElement('div');
            userTitle.classList.add('card-title');
            userTitle.classList.add('userTitle');

            const titleProfile = document.createElement('div');
            titleProfile.classList.add('profile');

            // const img = document.createElement('img');
            // img.src = user.avatar;
            // titleProfile.appendChild(img);

            const name = document.createElement('h3');
            name.innerHTML = user.first_name + " " + user.last_name;
            titleProfile.appendChild(name);

            const profile = document.createElement('a');
            profile.href = './profile?userId=' + user.id;
            const usrName = document.createElement('h5');
            usrName.innerHTML = '@' + user.username;
            profile.appendChild(usrName);
            titleProfile.appendChild(profile);


            userTitle.appendChild(titleProfile);

            elementBody.appendChild(userTitle);

            //request button
            const requestContainer = document.createElement('div');
            requestContainer.classList.add('card-title');
            requestContainer.classList.add('requestContainer');

            const requestBtn = document.createElement('input');
            requestBtn.setAttribute('type', 'button');
            requestBtn.setAttribute('id', 'requestBtn');
            requestBtn.classList.add('btn');

            //if user has already followed
            if (following.find((follow) => follow == user.username)) {
                if (followers.find((follow) => follow == user.username)) {
                    requestBtn.setAttribute('value', 'Friends');
                    requestBtn.classList.add('btn-secondary');
                }
                else {
                    requestBtn.setAttribute('value', 'Requested');
                    requestBtn.classList.add('btn-warning');
                }

            }
            else {
                requestBtn.classList.add('btn-primary');
                requestBtn.setAttribute('value', 'Friend Request');
            }

            requestContainer.appendChild(requestBtn);

            requestBtn.addEventListener('click', (e) => {
                if (following.find((follow) => follow == user.username)) {
                    api.unFollowUser(current.id, user.id).then((followData) => {
                        console.log(followData);
                        location.reload();
                    }).catch((err) => {
                        console.error('Error following user:', err);
                    });
                }
                else {
                    api.followUser(current.id, user.id).then((followData) => {
                        console.log(followData);
                        location.reload();
                    }).catch((err) => {
                        console.error('Error following user:', err);
                    });
                }
            });

            elementBody.appendChild(requestContainer);

            userElement.appendChild(elementBody);

            usersContainer.appendChild(userElement);
        }
    });
}

window.addEventListener("DOMContentLoaded", (e) => {
    api.getCurrentUser().then((current) => {

        api.getUsers().then((users) => {
            api.getUserFollowing(current.id).then((followingMap) => {
                console.log(followingMap.following);

                api.getUserFollowers(current.id).then((followerMap) => {
                    console.log(followerMap.followers);

                    displayUsers(current, users, followingMap.following, followerMap.followers);
                }).catch((err) => {
                    console.error('Error fetching followers:', err);
                });
            }).catch((err) => {
                console.error('Error fetching following:', err);
            });
        }).catch((err) => {
            console.error('Error fetching users:', err);
        });
    }).catch((err) => {
        if (err.status === 401) {
            console.log("We are not logged in");
            document.location = './login';
        }
        else {
            console.error('Error fetching users:', err);
        }
    });


});