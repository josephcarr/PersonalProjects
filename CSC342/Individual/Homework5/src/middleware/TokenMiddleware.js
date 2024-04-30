// const jwt = require('jsonwebtoken');
const crypto = require('crypto');
const { Buffer } = require('buffer');

const TOKEN_COOKIE_NAME = "Howler";
// In a real application, you will never hard-code this secret and you will
// definitely never commit it to version control, ever
const API_SECRET = process.env.API_SECRET_KEY;
// const API_SECRET = "60d0954e20eaa0c02b382171c33c53bc18522cc6d4805eaa02e182b0";

function validateJWT(token) {
  const encodedHeader = token.split('.')[0].trim();
  const encodedPayload = token.split('.')[1].trim();
  const signature = token.split('.')[2].trim();

  const expectedSignature = crypto.createHmac('sha256', API_SECRET).update(encodedHeader + '.' + encodedPayload).digest('base64url');

  if (signature != expectedSignature) {
    throw new Error('Invalid Token')
  }

  const payload = Buffer.from(encodedPayload, 'base64url').toString('utf8');
  const decoded = JSON.parse(payload);

  if (new Date(decoded.exp * 1000) < new Date()) {
    throw new Error('Invalid Token')
  }

  return decoded;
}

exports.TokenMiddleware = (req, res, next) => {
  // We will look for the token in two places:
  // 1. A cookie in case of a browser
  // 2. The Authorization header in case of a different client
  let token = null;
  if(!req.cookies[TOKEN_COOKIE_NAME]) {
    //No cookie, so let's check Authorization header
    const authHeader = req.get('Authorization');
    if(authHeader && authHeader.startsWith("Bearer ")) {
      //Format should be "Bearer token" but we only need the token
      token = authHeader.split(" ")[1].trim();
    }
  }
  else { //We do have a cookie with a token
    token = req.cookies[TOKEN_COOKIE_NAME]; //Get session Id from cookie
  }

  if(!token) { // If we don't have a token
    res.status(401).json({error: 'Not authenticated'});
    return;
  }

  //If we've made it this far, we have a token. We need to validate it

  try {
    // const decoded = jwt.verify(token, API_SECRET);
    const decoded = validateJWT(token);
    req.user = decoded.user;
    next(); //Make sure we call the next middleware
  }
  catch(err) { //Token is invalid
    res.status(401).json({error: 'Not authenticated'});
    return;
  }


}

function createJWT(payload) {
  const header = { alg: "HS256", typ: "JWT" }
  const headerString = JSON.stringify(header);
  const encodedHeader = Buffer.from(headerString, 'utf8').toString('base64url');

  const payloadString = JSON.stringify(payload);
  const encodedPayload = Buffer.from(payloadString, 'utf8').toString('base64url');

  const signature = crypto.createHmac('sha256', API_SECRET).update(encodedHeader + '.' + encodedPayload).digest('base64url');

  return `${encodedHeader}.${encodedPayload}.${signature}`;
}


exports.generateToken = (req, res, user) => {
  let data = {
    user: user,
    // Use the exp registered claim to expire token in 1 hour
    exp: Math.floor(Date.now() / 1000) + (60 * 60)
  }

  // const token = jwt.sign(data, API_SECRET);
  const token = createJWT(data);
  // console.log(token);


  //send token in cookie to client
  res.cookie(TOKEN_COOKIE_NAME, token, {
    httpOnly: true,
    secure: true,
    maxAge: 2 * 60 * 1000 //This session expires in 2 minutes.. but token expires in 1 hour!
  });
};


exports.removeToken = (req, res) => {
  //send session ID in cookie to client
  res.cookie(TOKEN_COOKIE_NAME, "", {
    httpOnly: true,
    secure: true,
    maxAge: -360000 //A date in the past
  });

}

