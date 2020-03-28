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
}

export default new RekomendasiService();