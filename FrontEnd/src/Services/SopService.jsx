import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class SopService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getSopList() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/SOP/getAll`);
    }
}

export default new SopService();