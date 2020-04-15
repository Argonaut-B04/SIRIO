// Axios digunakan untuk call API ke backend
import axios from "axios";
import SirioAxiosBase from "./SirioAxiosBase";

// Kelas ini fokus untuk authentikasi
class AuthenticationService {

    // Fungsi untuk login
    executeBasicAuthenticationService(username, password) {

        // Menggunakan Axios untuk memanggil API Login
        // Header berisi informasi token username dan password
        return axios.get(SirioAxiosBase.BASEURL + "/Employee/login",
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
        const { USERNAME_NAME_SESSION_ATTRIBUTE_NAME, ROLE_NAME_SESSION_ATTRIBUTE_NAME, TOKEN_SESSION_ATTRIBUTE_NAME } = SirioAxiosBase;
        sessionStorage.setItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME, username);
        sessionStorage.setItem(ROLE_NAME_SESSION_ATTRIBUTE_NAME, role);
        const token = this.createBasicAuthToken(username, password);
        sessionStorage.setItem(TOKEN_SESSION_ATTRIBUTE_NAME, token)
    }

    /**
     * Fungsi untuk mengambil username user yang sedang login
     * 
     * cara menggunakan: 
     * - Import AuthenticationService from ...
     * - AuthenticationService.getUsername();
     */

    getUsername() {
        return sessionStorage.getItem(SirioAxiosBase.USERNAME_NAME_SESSION_ATTRIBUTE_NAME);
    }

    /**
    * Fungsi untuk mengambil role user yang sedang login
    * 
    * cara menggunakan: 
    * - Import AuthenticationService from ...
    * - AuthenticationService.getUsername();
    */
    getRole() {
        return sessionStorage.getItem(SirioAxiosBase.ROLE_NAME_SESSION_ATTRIBUTE_NAME);
    }

    /**
     * Fungsi untuk mengambil logintoken
     */
    getToken() {
        return sessionStorage.getItem(SirioAxiosBase.TOKEN_SESSION_ATTRIBUTE_NAME);
    }

    /**
     * Fungsi untuk cek apakah user sudah login (username, role, dan token) valid
     */
    isLoggedIn() {
        return (this.getToken() && this.getUsername() && this.getRole());
    }

    /**
    * Fungsi untuk logout
    * 
    * cara menggunakan:
    * - window.location.href="/logout"
    */
    logout() {
        const { USERNAME_NAME_SESSION_ATTRIBUTE_NAME, ROLE_NAME_SESSION_ATTRIBUTE_NAME, TOKEN_SESSION_ATTRIBUTE_NAME } = SirioAxiosBase;

        sessionStorage.removeItem(USERNAME_NAME_SESSION_ATTRIBUTE_NAME);
        sessionStorage.removeItem(ROLE_NAME_SESSION_ATTRIBUTE_NAME);
        sessionStorage.removeItem(TOKEN_SESSION_ATTRIBUTE_NAME);
    }
}

export default new AuthenticationService();