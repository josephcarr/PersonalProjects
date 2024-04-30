import api from './APIClient.js';

function displayNav(user) {
    let navProfile = document.getElementById('navProfile');
    navProfile.innerHTML = '';

    const profile = document.createElement('a');
    profile.href = './profile?id=' + user.id;
    const usrName = document.createElement('p');
    usrName.innerHTML = '@' + user.username;
    profile.appendChild(usrName);
    navProfile.appendChild(profile);

    const img = document.createElement('img');
    img.src = user.avatar;
    navProfile.appendChild(img);

    const logoutButton = document.createElement('input');
    logoutButton.setAttribute('type', 'button');
    logoutButton.setAttribute('id', 'logoutButton');
    logoutButton.classList.add('btn');
    logoutButton.classList.add('btn-primary');
    logoutButton.setAttribute('value', 'Log Out');

    logoutButton.addEventListener('click', (e) => {
        api.logOut().then((logoutData) => {
            console.log(logoutData);
            location.reload();
        }).catch((err) => {
            console.error('Error logging out:', err);
        });

    });

    navProfile.appendChild(logoutButton);
}

function fillHowls(howls, users) {
    const howlsContainer = document.getElementById('howlsContainer');
    howlsContainer.innerHTML = ''; // Clear previous content
    howls.forEach(howl => {
        let user = users.find((user) => user.id == howl.userId);

        const howlElement = document.createElement('div');
        howlElement.classList.add('card', 'mb-3');

        const elementBody = document.createElement('div');
        elementBody.classList.add('card-body');

        // Title
        const howlTitle = document.createElement('div');
        howlTitle.classList.add('card-title');
        howlTitle.classList.add('howlTitle');

        const titleProfile = document.createElement('div');
        titleProfile.classList.add('profile');

        const img = document.createElement('img');
        img.src = user.avatar;
        titleProfile.appendChild(img);

        const name = document.createElement('h3');
        name.innerHTML = user.first_name + " " + user.last_name;
        titleProfile.appendChild(name);

        const profile = document.createElement('a');
        profile.href = './profile?id=' + user.id;
        const usrName = document.createElement('h5');
        usrName.innerHTML = '@' + user.username;
        profile.appendChild(usrName);
        titleProfile.appendChild(profile);


        howlTitle.appendChild(titleProfile);

        const titleTime = document.createElement('div');
        titleTime.classList.add('howlDate');

        const howlDate = document.createElement('p');
        const date = new Date(howl.datetime);
        const formattedDate = date.toLocaleString('en-US', { timeZoneName: 'short' });
        howlDate.innerHTML = formattedDate;
        titleTime.appendChild(howlDate);

        howlTitle.appendChild(titleTime);

        elementBody.appendChild(howlTitle);

        //Text
        const howlText = document.createElement('p');
        howlText.classList.add('card-text');
        howlText.innerHTML = howl.text;

        elementBody.appendChild(howlText);

        howlElement.appendChild(elementBody);

        howlsContainer.appendChild(howlElement);
    });
}

// Function to fetch and display howls
function displayHowls(userId) {
    api.getHowlsByUser(userId).then((howls) => {
        howls.sort((a, b) => {
            let aDate = new Date(a.datetime);
            let bDate = new Date(b.datetime);

            return bDate - aDate;
        })

        api.getUsers().then((users) => {
            fillHowls(howls, users);
        }).catch((err) => {
            if (err.status === 401) {
                console.log("We are not logged in");
                document.location = './';
            }
            else {
                console.error('Error fetching users:', err);
            }
        });
    }).catch((err) => {
        if (err.status === 401) {
            console.log("We are not logged in");
            document.location = './';
        }
        else {
            console.error('Error fetching howls:', err);
        }
    });
}

function fillFollowers(user) {
    api.getFollowsByUser(user.id).then((follows) => {
        let followContain = document.getElementById('collapseOne');
        followContain.innerHTML = '';

        follows.forEach((follow) => {
            let flwUser = document.createElement('div');
            flwUser.classList.add('card-body');
            flwUser.classList.add('follow');

            let flwPic = document.createElement('div');
            const pic = document.createElement('img');
            pic.src = follow.avatar;
            flwPic.appendChild(pic);
            flwUser.appendChild(flwPic);

            let flwInfo = document.createElement('div');
            flwInfo.classList.add('info');

            let name = document.createElement('h5');
            name.innerHTML = follow.first_name + ' ' + follow.last_name;
            flwInfo.appendChild(name);

            let profile = document.createElement('a');
            profile.href = './profile?id=' + follow.id;
            let usrName = document.createElement('p');
            usrName.innerHTML = '@' + follow.username;
            profile.appendChild(usrName);
            flwInfo.appendChild(profile);

            flwUser.appendChild(flwInfo);

            followContain.appendChild(flwUser);
        })
    }).catch((err) => {
        console.error('Error fetching follows:', err);
    });
}

function displayProfile(user, current) {
    //Profile Info
    let profilePic = document.getElementById('profilePic');
    profilePic.innerHTML = '';
    const pic = document.createElement('img');
    pic.src = user.avatar;
    profilePic.appendChild(pic);

    let profileInfo = document.getElementById('profileInfo');
    profileInfo.innerHTML = '';

    const elementBody = document.createElement('div');
    elementBody.classList.add('card-body');

    let name = document.createElement('h5');
    name.classList.add('card-title');
    name.innerHTML = user.first_name + ' ' + user.last_name;
    elementBody.appendChild(name);

    let profile = document.createElement('a');
    profile.href = './profile?id=' + user.id;
    let usrName = document.createElement('p');
    usrName.innerHTML = '@' + user.username;
    profile.appendChild(usrName);
    elementBody.appendChild(profile);

    let followButton = document.createElement('input');
    followButton.setAttribute('type', 'button');
    followButton.setAttribute('id', 'followButton');
    followButton.classList.add('btn');

    api.getFollowsByUser(current.id).then((follows) => {
        if (user.id != current.id) {
            //if user has already followed
            if (follows.find((follow) => follow.id == user.id)) {
                followButton.classList.add('btn-secondary');
                followButton.setAttribute('value', 'Followed');
            }
            else {
                followButton.classList.add('btn-primary');
                followButton.setAttribute('value', 'Follow');
            }
            
            elementBody.appendChild(followButton);

            followButton.addEventListener('click', (e) => {
                api.putFollow(current.id, user.id).then(followData => {
                    console.log(followData);
                    location.reload();
                }).catch((err) => {
                    console.error('Error following user:', err);
                });
            });
        }
    }).catch((err) => {
        console.error('Error fetching follows:', err);
    });

    profileInfo.appendChild(elementBody);
}

window.addEventListener("DOMContentLoaded", (e) => {
    const query = window.location.search;
    let parameters = new URLSearchParams(query);
    let id = parameters.get('id');

    api.getCurrentUser().then((current) => {
        displayNav(current);

        api.getUserById(id).then((user) => {
            displayProfile(user, current);

            fillFollowers(user);

            // Fetch and display howls when the page loads
            displayHowls(user.id);
        }).catch((err) => {
            console.error('Error fetching user:', err);
        });
    }).catch((err) => {
        if (err.status === 401) {
            console.log("We are not logged in");
            document.location = './';
        }
        else {
            console.error('Error fetching howls:', err);
        }
    });


});