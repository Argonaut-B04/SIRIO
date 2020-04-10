import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class BuktiPelaksanaanService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async addBuktiPelaksanaan(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/BuktiPelaksanaan/tambah`, data);
    }

}

export default new BuktiPelaksanaanService();