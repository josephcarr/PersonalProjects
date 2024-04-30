import api from './APIClient.js';

window.addEventListener("DOMContentLoaded", async () => {
    try {
        const urlParams = new URLSearchParams(window.location.search);
        const userIdParam = urlParams.get('userId');

        const user = await api.getUserById(userIdParam);
        
        document.querySelector('.card-body h5').textContent = `${user.first_name} ${user.last_name}`;

        // Fetch the current logged-in user
        const currentUser = await api.getCurrentUser();

        // Check if the user ID parameter matches the ID of the current logged-in user
        const canEdit = currentUser.id == userIdParam;

        // Now you can use the `canEdit` variable to determine whether to display the "Edit" button
        if (canEdit) {
            // Display the "Edit" button
            document.getElementById('editButton').style.display = 'block';
            document.getElementById('messageButton').style.display = 'none';
        } else {
            // Hide the "Edit" button
            document.getElementById('editButton').style.display = 'none';
            document.getElementById('messageButton').style.display = 'block';
            document.getElementById('chooseAvatarButton').style.display = 'none';
        }

        document.getElementById('first').textContent = user.first_name;
        document.getElementById('last').textContent = user.last_name;
        document.getElementById('email').textContent = user.email;
        document.getElementById('username').textContent = user.username;
        document.getElementById('parks').textContent = user.num_parks;
    } catch (err) {
        if (err.status === 401) {
            console.log("We are not logged in");
            document.location = './login';
        } else {
            console.error('Error fetching current user:', err);
        }
    }
});

document.getElementById('chooseAvatarButton').addEventListener('click', function() {
    document.getElementById('avatarInput').click();
});

document.getElementById('avatarInput').addEventListener('change', function() {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(event) {
            const imgPreview = document.createElement('img');
            imgPreview.src = event.target.result;
            imgPreview.style.maxWidth = '120px'; // Adjust image preview size as needed
            imgPreview.style.maxHeight = '120px';
            document.getElementById('avatarPreview').innerHTML = ''; // Clear previous preview
            document.getElementById('avatarPreview').appendChild(imgPreview);
        };
        reader.readAsDataURL(file);
    }
});
