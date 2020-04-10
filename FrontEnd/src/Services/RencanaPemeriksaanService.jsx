import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RencanaPemeriksaanService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getRencanaPemeriksaanByLoggedInUser() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/RencanaPemeriksaan/getAll`);
    }

    async getRencanaPemeriksaanDetail(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/RencanaPemeriksaan/` + id);
    }

    async addRencanaPemeriksaanCabang(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/RencanaPemeriksaan/tambah`, data);
    }
}

export default new RencanaPemeriksaanService();