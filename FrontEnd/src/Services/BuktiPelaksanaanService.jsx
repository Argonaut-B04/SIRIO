import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class BuktiPelaksanaanService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async submitChanges(idRekomendasi, data) {
        const dataToPass = {
            id: idRekomendasi,
            bukti: data
        }
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/BuktiPelaksanaan/tambah`, dataToPass);
    }

}

export default new BuktiPelaksanaanService();