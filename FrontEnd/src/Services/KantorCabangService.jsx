import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class KantorCabangService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getKantorCabangByLoggedInUser() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/KantorCabang/getAll`);
    }

    async getKantorCabangDetail(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/KantorCabang/` + id);
    }

    async addKantorCabang(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/KantorCabang/tambah`, data);
    }
}

export default new KantorCabangService();