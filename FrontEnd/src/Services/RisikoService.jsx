import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RisikoService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getAll() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Risiko/getAll`);
    }

    async getAllChild() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/Risiko/getAll/child');
    }
}

export default new RisikoService();