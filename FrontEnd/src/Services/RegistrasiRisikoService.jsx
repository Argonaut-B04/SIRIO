import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RegistrasiRisikoService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getAllRisiko() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/Risiko/getAll');
    }

    async submitChanges(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Risiko/tambah', data);
    }

    async getRisiko(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Risiko/` + id);
    }

    async hapusRisiko(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Risiko/hapus', data);
    }

    async ubahRisiko(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Risiko/ubah', data)
    }

    async checkRisikoExist(nama) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/Risiko/check/' + nama)
    }
}

export default new RegistrasiRisikoService();