import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class HasilPemeriksaanService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getHasilPemeriksaanByLoggedInUser() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/HasilPemeriksaan/getTabelHasilPemeriksaan`);
    }

    async getHasilPemeriksaan(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/HasilPemeriksaan/` + id);
    }

    async deleteHasilPemeriksaan(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/HasilPemeriksaan/hapus`, data)
    }

    async setujuiHasilPemeriksaan(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/HasilPemeriksaan/persetujuan', data)
    }

    async addHasilPemeriksaan(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/HasilPemeriksaan/tambah', data)
    }

    async editHasilPemeriksaan(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/HasilPemeriksaan/ubah', data)
    }

    async jalankan(id_hasil) {
        console.log(id_hasil);
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/HasilPemeriksaan/jalankan/' + id_hasil)
    }
}

export default new HasilPemeriksaanService();