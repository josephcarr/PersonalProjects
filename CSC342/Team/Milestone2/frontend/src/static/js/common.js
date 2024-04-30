import api from './APIClient.js';

window.addEventListener("DOMContentLoaded", (e) => {
    // Fetch the current user's ID using your API client
    api.getCurrentUser().then((currentUser) => {
      console.log(currentUser);
      const userId = currentUser.id;
      const profileUrl = `/profile?userId=${userId}`;

      // Get the Profile link element by ID
      const profileLink = document.getElementById("profile-link");
      
      // Set the href attribute of the Profile link to the dynamically generated URL
      profileLink.href = profileUrl;
    }).catch((err) => {
      if (err.status === 401) {
        console.log("We are not logged in");
        document.location = './login';
      } else {
        console.error('Error fetching current user:', err);
      }
    });
  });