const express = require('express');

const frontendRouter = express.Router();
frontendRouter.use(express.json());

const path = require('path');
const html_path = path.resolve(__dirname + '/../../templates/');

// Set up Middleware
frontendRouter.use(express.static('static'));
frontendRouter.use(express.urlencoded({extended: true}));

// Routes
frontendRouter.get('/', (req, res) => {
    res.sendFile(`${html_path}/login.html`);
});

frontendRouter.get('/login', (req, res) => {
    res.sendFile(`${html_path}/login.html`);
});

frontendRouter.get('/main', (req, res) => {
    res.sendFile(`${html_path}/main.html`);
});

frontendRouter.get('/profile', (req, res) => {
    res.sendFile(`${html_path}/profile.html`);
});

frontendRouter.get('/register', (req, res) => {
    res.sendFile(`${html_path}/register.html`);
});

module.exports = frontendRouter;