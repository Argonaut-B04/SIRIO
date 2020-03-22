import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/Employee';
const USERNAME_NAME_SESSION_ATTRIBUTE_NAME = "SirioInSession";
const ROLE_NAME_SESSION_ATTRIBUTE_NAME = "SirioRoleInSession";

class AuthenticationService {
    executeBasicAuthenticationService(username, password) {
        return axios.get(`${API_URL}/login`,
            {
                headers: {
                    authorization: this.createBasicAuthToken(username, password)
                }
            }
        )
    }

    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password);
    }

    registerSuccessfulLogin(username, password, role) {
        sessionStorage.setItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME, username);
        sessionStorage.setItem(ROLE_NAME_SESSION_ATTRIBUTE_NAME, role);
        this.setupAxiosInterceptors(this.createBasicAuthToken(username, password));
    }

    setupAxiosInterceptors(token) {
        axios
            .interceptors
            .request
            .use(
                (config) => {
                    if (this.isUserLoggedIn()) {
                        config.headers.authorization = token
                    };
                    return config;
                }
            )
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME);
        if (user == null) return '';
        return user;
    }

    getUsername() {
        return sessionStorage.getItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME);
    }

    getRole() {
        return sessionStorage.getItem(ROLE_NAME_SESSION_ATTRIBUTE_NAME);
    }

    logout() {
        sessionStorage.removeItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME);
        sessionStorage.removeItem(ROLE_NAME_SESSION_ATTRIBUTE_NAME);
    }
}

export default new AuthenticationService();