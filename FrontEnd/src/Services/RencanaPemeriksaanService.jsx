import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RencanaPemeriksaanService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getRencanaPemeriksaanList() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/RencanaPemeriksaan/getAll`);
    }

    async getRencanaPemeriksaanDetail(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/RencanaPemeriksaan/` + id);
    }

    async addRencanaPemeriksaan(data) {
        console.log(data)
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/RencanaPemeriksaan/tambah`, data);
    }

    async editRencanaPemeriksaan(data) {
        console.log(data);
        console.log(SirioAxiosBase.BASEURL + '/RencanaPemeriksaan/ubah');
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/RencanaPemeriksaan/ubah', data)
    }

    async deleteRencanaPemeriksaan(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/RencanaPemeriksaan/hapus`, data)
    }

}

export default new RencanaPemeriksaanService();