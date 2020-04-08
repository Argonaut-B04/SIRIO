import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RiskRatingService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getAll() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/RiskRating`);
    }

    async submitChanges(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/RiskRating', data)
    }
}

export default new RiskRatingService();