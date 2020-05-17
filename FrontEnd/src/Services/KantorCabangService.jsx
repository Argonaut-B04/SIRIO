import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class KantorCabangService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getKantorCabangList() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/KantorCabang/getAll`);
    }

    async getKantorCabangDetail(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/KantorCabang/` + id);
    }

    async getKantorCabang() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/KantorCabang/pemilik/`);
    }

    async addKantorCabang(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/KantorCabang/tambah`, data);
    }

    async isExistKantorCabang(string) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/KantorCabang/check/` + string);
    }

    async editKantorCabang(data) {
        console.log(data);
        console.log(SirioAxiosBase.BASEURL + '/KantorCabang/ubah');
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/KantorCabang/ubah', data)
    }

    async deleteKantorCabang(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/KantorCabang/hapus`, data)
    }

}

export default new KantorCabangService();