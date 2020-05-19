import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class TugasPemeriksaanService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getTugasPemeriksaanByLoggedInUser() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/TugasPemeriksaan/getByEmployee`);
    }

    async deleteTugas(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/TugasPemeriksaan/hapus`, data)
    }
}

export default new TugasPemeriksaanService();