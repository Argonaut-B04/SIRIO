import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class BuktiPelaksanaanService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async addBukti(idRekomendasi, data) {
        const dataToPass = {
            idRekomendasi: idRekomendasi,
            keterangan: data.keterangan,
            lampiran: data.lampiran
        }
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/BuktiPelaksanaan/tambah`, dataToPass);
    }

    async editBukti(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/BuktiPelaksanaan/ubah', data)
    }

    async setStatusBukti(idBuktiPelaksanaan, data) {
        const dataToPass = {
            id: idBuktiPelaksanaan,
            status: data.status,
            statusRekomendasi: data.statusRekomendasi,
            feedback: data.feedback
        }
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/BuktiPelaksanaan/persetujuan', dataToPass)
    }

    async getBuktiPelaksanaan(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/BuktiPelaksanaan/` + id);
    }

}

export default new BuktiPelaksanaanService();