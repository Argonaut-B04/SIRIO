import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class HasilPemeriksaanService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getHasilPemeriksaanByLoggedInUser() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/HasilPemeriksaan/getTabelHasilPemeriksaan`);
    }

    async getHasilPemeriksaan(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/HasilPemeriksaan/` + id);
    }
}

export default new HasilPemeriksaanService();