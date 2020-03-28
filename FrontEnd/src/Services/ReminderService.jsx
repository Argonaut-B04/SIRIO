import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class ReminderService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getByIdRekomendasi(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/Reminder/getByRekomendasi`, data);
    }

    async delete(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/Reminder/hapus`, data)
    }
}

export default new ReminderService();