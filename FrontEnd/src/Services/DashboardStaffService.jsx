import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class DashboardStaffService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getAllData(tanggalAwal, tanggalAkhir) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/DashboardStaff/getAllData', {params:
            {tanggalAwal: tanggalAwal, tanggalAkhir: tanggalAkhir}});
    }

    async getDashboardQA(id, tanggalAwal, tanggalAkhir) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/DashboardStaff/qa`, {params:
        {id: id, tanggalAwal: tanggalAwal, tanggalAkhir: tanggalAkhir}});
    }
}

export default new DashboardStaffService();