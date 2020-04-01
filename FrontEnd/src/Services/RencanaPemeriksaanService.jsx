import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RencanaPemeriksaanService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getRencanaPemeriksaanByLoggedInUser() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/RencanaPemeriksaan/getAll`);
    }
}

export default new RencanaPemeriksaanService();