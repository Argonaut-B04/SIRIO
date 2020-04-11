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
        console.log(data);
        console.log(SirioAxiosBase.BASEURL + '/BuktiPelaksanaan/ubah');
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/BuktiPelaksanaan/ubah', data)
    }

    async statusBukti(idBuktiPelaksanaan, data) {
        const dataToPass = {
            idBuktiPelaksanaan: idBuktiPelaksanaan,
            status: data.status,
            feedback: data.feedback
        }
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/BuktiPelaksanaan/persetujuan', dataToPass)
    }

    async getBuktiPelaksanaan(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/BuktiPelaksanaan/` + id);
    }

    async submitPersetujuan(id, data) {
        const dataToPass = {
            id: id,
            feedback: data.feedback
        }
        return this.axiosInstance.put(SirioAxiosBase.BASEURL + `/BuktiPelaksanaan/persetujuan`, dataToPass)
    }

}

export default new BuktiPelaksanaanService();