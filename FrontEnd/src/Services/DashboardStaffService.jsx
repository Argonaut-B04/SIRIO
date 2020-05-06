import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class DashboardStaffService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getAllData() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + '/DashboardStaff/getAllData');
    }
}

export default new DashboardStaffService();