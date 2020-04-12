import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class HierarkiRisikoService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getAllRisiko() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/Risiko/getAll');
    }

    async submitChanges(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Risiko/ubah-hierarki', data);
    }

    async getByKategori() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/Risiko/ubah-hierarki/kategori');
    }
}

export default new HierarkiRisikoService();