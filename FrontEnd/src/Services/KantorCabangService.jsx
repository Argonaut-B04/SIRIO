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
}

export default new KantorCabangService();