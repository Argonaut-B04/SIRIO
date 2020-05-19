import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class SopService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getSopList() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/SOP/getAll`);
    }

    async getSOPDetail(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/SOP/` + id);
    }

    async addSOP(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/SOP/tambah`, data);
    }

    async isExistSOP(string) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/SOP/check/` + string);
    }

    async editSOP(data) {
        console.log(data);
        console.log(SirioAxiosBase.BASEURL + '/SOP/ubah');
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/SOP/ubah', data)
    }

    async deleteSOP(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/SOP/hapus`, data)
    }
}

export default new SopService();