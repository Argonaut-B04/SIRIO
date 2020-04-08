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
            idRekomendasi: idRekomendasi,
            keterangan: data.keterangan,
            lampiran: data.lampiran
        }
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/BuktiPelaksanaan/tambah`, dataToPass);
    }

    async getBuktiPelaksanaan(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/BuktiPelaksanaan/` + id);
    }

    async setujuiBukti(id) {
        return this.axiosInstance.put(SirioAxiosBase.BASEURL + `/BuktiPelaksanaan/persetujuan`, id)
    }

}

export default new BuktiPelaksanaanService();