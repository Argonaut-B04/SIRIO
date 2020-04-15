import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RekomendasiService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getRekomendasiByLoggedInUser() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Rekomendasi/getAll`);
    }

    async setTenggatWaktu(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/Rekomendasi/tenggatWaktu`, data)
    }

    async getRekomendasi(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Rekomendasi/` + id);
    }
}

export default new RekomendasiService();