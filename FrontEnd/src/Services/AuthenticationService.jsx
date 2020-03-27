// Axios digunakan untuk call API ke backend
import axios from 'axios';

// Variabel setting backend
const API_URL = 'http://localhost:8080/api/v1/';
const USERNAME_NAME_SESSION_ATTRIBUTE_NAME = "SirioInSession";
const ROLE_NAME_SESSION_ATTRIBUTE_NAME = "SirioRoleInSession";

// Ini url utama yang akan dijalankan pada service ini
const URL = API_URL + '/Employee';

// Kelas ini fokus untuk authentikasi
class AuthenticationService {

    // Fungsi untuk login
    executeBasicAuthenticationService(username, password) {

        // Menggunakan Axios untuk memanggil API Login
        // Header berisi informasi token username dan password
        return axios.get(`${URL}/login`,
            {
                headers: {
                    authorization: this.createBasicAuthToken(username, password)
                }
            }
        )
    }

    // Fungsi untuk membuat token userame dan password
    // Formatnya sudah fix, Basic + username dan password yang dienkripsi bit64
    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password);
    }

    // Simpan informasi username dan role user yang sudah login kedalam sessionstorage
    registerSuccessfulLogin(username, password, role) {
        sessionStorage.setItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME, username);
        sessionStorage.setItem(ROLE_NAME_SESSION_ATTRIBUTE_NAME, role);
        this.setupAxiosInterceptors(this.createBasicAuthToken(username, password));
    }

    // Fungsi interceptor axios, agar headers nya tidak perlu ditulis lagi jika sudah login
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

    /**
     * Fungsi untuk mengambil username user yang sedang login
     * 
     * cara menggunakan: 
     * - Import AuthenticationService from ...
     * - AuthenticationService.isUserLoggedIn();
     */
    isUserLoggedIn() {
        let user = sessionStorage.getItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME);
        if (user == null) return '';
        return user;
    }

    /**
     * Fungsi untuk mengambil username user yang sedang login
     * 
     * cara menggunakan: 
     * - Import AuthenticationService from ...
     * - AuthenticationService.getUsername();
     */

    getUsername() {
        return sessionStorage.getItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME);
    }

    /**
    * Fungsi untuk mengambil role user yang sedang login
    * 
    * cara menggunakan: 
    * - Import AuthenticationService from ...
    * - AuthenticationService.getUsername();
    */
    getRole() {
        return sessionStorage.getItem(ROLE_NAME_SESSION_ATTRIBUTE_NAME);
    }

    /**
    * Fungsi untuk logout
    * 
    * cara menggunakan:
    * - window.location.href="/logout"
    */
    logout() {
        sessionStorage.removeItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME);
        sessionStorage.removeItem(ROLE_NAME_SESSION_ATTRIBUTE_NAME);
    }
}

export default new AuthenticationService();