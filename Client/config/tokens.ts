export function getAuthToken() {
  return localStorage.getItem("auth-token");
}

export function setAuthToken(authToken: string) {
  localStorage.setItem("auth-token", authToken);
}

export function removeAuthToken() {
  localStorage.removeItem("auth-token");
}
