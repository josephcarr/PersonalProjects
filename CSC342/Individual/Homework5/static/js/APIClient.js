import HTTPClient from "./HTTPClient.js";

const API_BASE = `./api`;

const logIn = (username, password) => {
  const data = {
    username: username,
    password: password
  }
  return HTTPClient.post(`${API_BASE}/login`, data);
};

const logOut = () => {
  return HTTPClient.post(`${API_BASE}/logout`, {});
};

const getCurrentUser = () => {
  return HTTPClient.get(`${API_BASE}/users/current`);
};

const getUsers = () => {
  return HTTPClient.get(`${API_BASE}/users`);
}

const getUserById = (userId) => {
  return HTTPClient.get(`${API_BASE}/users/id/` + userId);
}

const postUser = (username, first_name, last_name, password) => {
  const data = {
    username: username,
    first_name: first_name,
    last_name: last_name,
    password: password
  }

  return HTTPClient.post(`${API_BASE}/register`, data);
}

const putFollow = (currentUser, followUser) => {
  const data = {
    currentId: currentUser,
    followerId: followUser
  };

  return HTTPClient.put(`${API_BASE}/users/id/` + currentUser + `/following`, data);
}

const getFollowsByUser = (userId) => {
  return HTTPClient.get(`${API_BASE}/follows/usr/` + userId);
}

const postHowl = (user, howlText) => {
  const data = {
    userId: user.id, text: howlText
  };

  return HTTPClient.post(`${API_BASE}/howls`, data);
}

const getHowlsByUser = (userId) => {
  return HTTPClient.get(`${API_BASE}/howls/usr/` + userId);
}

const getHowlsForUser = (userId) => {
  return HTTPClient.get(`${API_BASE}/howls/` + userId);
}

export default {
  logIn,
  logOut,
  getCurrentUser,
  getUsers,
  getUserById,
  postUser,
  putFollow,
  getFollowsByUser,
  postHowl,
  getHowlsByUser,
  getHowlsForUser
};



