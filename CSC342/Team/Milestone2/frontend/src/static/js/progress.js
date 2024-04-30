import api from './APIClient.js';

window.addEventListener("DOMContentLoaded", async (e) => {
    try {
        // Fetch the current user's data
        const currentUser = await api.getCurrentUser();
        
        // Get the current user's ID
        const userId = currentUser.id;

        // Call the API to get the list of visited parks
        const visitedParks = await api.getVisitedParks(userId);
        console.log(visitedParks); // Log the visited parks to the console or do further processing

        const numberOfVisitedParks = visitedParks.length;

        const lastVisitedPark = visitedParks[visitedParks.length - 1];
        const lastVisitedParkId = lastVisitedPark.park_id;
        const lastVisitedParkDetails = await api.getParkDetailsById(lastVisitedParkId);
        console.log(lastVisitedParkDetails);

        // Get the name of the last visited park
        const lastVisitedParkName = lastVisitedParkDetails.name;
        if (numberOfVisitedParks > 0) {
            const noviceText = document.querySelector('.novice-text');

            // Change the color to green
            noviceText.style.color = 'green';

        }

        // Update the HTML element with the name of the last visited park
        document.getElementById('totalParksVisited').textContent = numberOfVisitedParks;
        document.getElementById('parksVisitedThisMonth').textContent = numberOfVisitedParks;
        document.getElementById('lastVisitedParkName').textContent = lastVisitedParkName;


    } catch (err) {
        if (err.status === 401) {
            console.log("We are not logged in");
            document.location = './login';
        } else {
            console.error('Error fetching user progress:', err);
        }
    }
});