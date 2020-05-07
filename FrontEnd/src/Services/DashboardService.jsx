import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class DashboardService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getDashboard() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Dashboard/getAll`);
    }

}

export default new DashboardService();