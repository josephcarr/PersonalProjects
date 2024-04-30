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
    api.getHowlsForUser(userId).then((howls) => {
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

window.addEventListener("DOMContentLoaded", (e) => {
    api.getCurrentUser().then((user) => {
        displayNav(user);

        let errorBox = document.getElementById('error');

        let howlInput = document.getElementById('howlText');
        howlInput.addEventListener("invalid", (e) => {
            howlInput.setCustomValidity('Must input a mesage to howl');
        });

        howlInput.addEventListener("input", (e) => {
            howlInput.setCustomValidity('');
            errorBox.textContent = '';
        });

        // Function to handle form submission for posting a new howl
        let form = document.getElementById('postHowlForm');
        form.addEventListener('submit', async function (event) {
            event.preventDefault();
            const howlText = howlInput.value;
            api.postHowl(user, howlText).then((howlData) => {
                console.log(howlData);
                displayHowls(user.id);
            }).catch((err) => {
                console.error('Error posting howl:', err);
                errorBox.textContent = err;
            });
        });

        // Fetch and display howls when the page loads
        displayHowls(user.id);
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