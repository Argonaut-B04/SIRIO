import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RegistrasiRisikoService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getRisiko() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/Risiko/getAll');
    }

    async submitChanges(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Risiko/tambah', data);
    }
}

export default new RegistrasiRisikoService();