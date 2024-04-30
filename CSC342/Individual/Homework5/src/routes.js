const express = require('express');
const router = express.Router();

const frontendRouter = require('./frontend/FrontendRoutes.js');
const apiRouter = require('./api/APIRoutes.js');

router.use(frontendRouter);
router.use('/api', apiRouter);

module.exports = router;

