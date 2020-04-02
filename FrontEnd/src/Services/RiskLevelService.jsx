import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RiskLevelService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getAll() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/RiskLevel/Aktif`);
    }

    async submitChanges(data) {
        const dataToPass = {
            levels: data
        }
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/RiskLevel', dataToPass)
    }
}

export default new RiskLevelService();